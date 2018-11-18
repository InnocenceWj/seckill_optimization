package com.wj.service.impl;

import com.wj.mapper.SeckillGoodsMapper;
import com.wj.redis.GoodKey;
import com.wj.redis.RedisDao;
import com.wj.redis.SeckillKey;
import com.wj.service.SeckillGoodsService;
import com.wj.utils.MD5Util;
import com.wj.utils.PageData;
import com.wj.utils.UUIDUtil;
import com.wj.utils.UserUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import static com.wj.utils.VerfyUtil.calc;
import static com.wj.utils.VerfyUtil.generateVerifyCode;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Resource
    private RedisDao redisDao;
    @Resource
    private SeckillGoodsMapper goodsMapper;

    @Override
    public BufferedImage createVerifyCode(String token, long goodsId) {
        if (token == "" || goodsId < 0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode + "=", 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisDao.setObj(SeckillKey.getSeckillVerifyCode.getPrefix() + token, goodsId, rnd, SeckillKey.getSeckillVerifyCode.expireSeconds());
        //输出图片
        return image;
    }

    @Override
    public boolean checkVerfyCode(PageData pd) {
        String token = pd.getString("token");
        Long goodId = Long.parseLong(pd.getString("goodId"));
        int verifyCode = Integer.valueOf(pd.getString("verifyCode"));
        if (goodId <= 0) {
            return false;
        }
        int oldCode = redisDao.getObj(SeckillKey.getSeckillVerifyCode.getPrefix() + token, goodId, Integer.class);
        if (oldCode - verifyCode != 0) {
            return false;
        }
        redisDao.delPattern(SeckillKey.getSeckillVerifyCode.getPrefix() + token, goodId);
        return true;
    }

    @Override
    public String createPath(PageData pd, long goodId) {
        String token = pd.getString("token");
        String md5 = MD5Util.md5(UUIDUtil.uuid() + goodId);
        redisDao.setString(SeckillKey.getSeckillPath.getPrefix()+token,goodId,md5);
        return md5;
    }

    @Override
    public boolean checkPath(PageData pd, long goodId) {
        String token = UserUtils.getToken();
        String md5 = pd.getString("md5");
        String md5Old = redisDao.getString(SeckillKey.getSeckillPath.getPrefix()+token,goodId);
//
        if(md5.equals(md5Old)){
            return true;
        }
        return false;
    }

    @Override
    public int reduceStock(long goodId) {
        PageData pd = redisDao.getObj(GoodKey.goodsDetail.getPrefix(),goodId, PageData.class);
        int seckillStock= (int) pd.get("stock_count");
        int goodStock= (int) pd.get("goods_stock");
        seckillStock=seckillStock-1;
        goodStock=goodStock-1;
        pd.put("stock_count",seckillStock);
        pd.put("goods_stock",goodStock);
        redisDao.setGood(GoodKey.goodsDetail.getPrefix(),goodId,pd);
        return seckillStock;
    }

    @Override
    public PageData getStockById(long goodId) {
        return goodsMapper.getStockById(goodId);
    }


}
