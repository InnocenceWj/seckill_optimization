package com.wj.service.impl;

import com.wj.entity.SeckillUser;
import com.wj.mapper.GoodMapper;
import com.wj.redis.RedisDao;
import com.wj.redis.SeckillKey;
import com.wj.service.SeckillGoodsService;
import com.wj.utils.PageData;
import com.wj.utils.VerfyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
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
    private SeckillGoodsService seckillGoodsService;

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
        redisDao.setVerfy(SeckillKey.getSeckillVerifyCode, token + "," + goodsId, rnd);
        //输出图片
        return image;
    }

    @Override
    public boolean checkVerfyCode(PageData pd) {
        String token=pd.getString("token");
        Long goodId= (Long) pd.get("goodId");
        int verifyCode=(Integer) pd.get("verifyCode");
        if (goodId <= 0) {
            return false;
        }
        int oldCode=redisDao.getVerfy(SeckillKey.getSeckillVerifyCode,token + "," + goodId,Integer.class);
        if (oldCode - verifyCode != 0) {
            return false;
        }
        redisDao.deleteVerfy(SeckillKey.getSeckillVerifyCode, token + "," + goodId);
        return true;
    }


}
