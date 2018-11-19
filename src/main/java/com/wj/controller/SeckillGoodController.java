package com.wj.controller;

import com.wj.amqp.MQSender;
import com.wj.amqp.SeckillMessage;
import com.wj.controller.baseController.BaseController;
import com.wj.entity.SeckillUser;
import com.wj.result.CodeMsg;
import com.wj.result.Result;
import com.wj.service.SeckillGoodsService;
import com.wj.service.SeckillOrderService;
import com.wj.utils.MapUtil;
import com.wj.utils.PageData;
import com.wj.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

/**
 * @创建人 wj
 * @创建时间 2018/11/14
 * @描述
 */
@Controller
@RequestMapping(value = "/seckill")
public class SeckillGoodController extends BaseController {
    @Resource
    private SeckillGoodsService seckillGoodsService;
    @Resource
    private SeckillOrderService seckillOrderService;
    @Resource
    private MQSender mqSender;


    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public Result<PageData> time() {
        Date date = new Date();
        PageData pd = new PageData();
        pd.put("nowTime", date);
        return Result.success(pd);
    }


    @RequestMapping("/verifyCode")
    @ResponseBody
    public Result<String> getVerifyCode(HttpServletResponse response, HttpServletRequest request,
                                        @RequestParam("goodsId") long goodsId) {
        response.setContentType("application/json;charset=UTF-8");
        String token = UserUtils.getToken();
        BufferedImage image = seckillGoodsService.createVerifyCode(token, goodsId);
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            outputStream.flush();
            outputStream.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
    }

    @RequestMapping(value = "/checkVerfyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> checkVerfyCode(HttpServletRequest request) {
        PageData pd = this.getPageData();
        String tokenS = UserUtils.getToken();
        String token = pd.getString("token");
        if (tokenS.equals(token)) {
            boolean flag = seckillGoodsService.checkVerfyCode(pd);
            return Result.success(flag);
        } else {
            return Result.error(CodeMsg.LOGIN_ERROR);
        }
    }

    @RequestMapping(value = "/{goodId}/exposer", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result<String> exposer(@PathVariable(value = "goodId") long goodId) {
        PageData pd = this.getPageData();
        try {
            String md5 = seckillGoodsService.createPath(pd, goodId);
            return Result.success(md5);
        } catch (Exception e) {
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
    }

    @RequestMapping(value = "/{goodId}/excution", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result<Integer> excution(@PathVariable(value = "goodId") long goodId) {
        PageData pd = this.getPageData();
        boolean flag = seckillGoodsService.checkPath(pd, goodId);
        if (!flag) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
//     1.先查看有没有该用户购买该商品的明细，有的话，返回秒杀重复，没有则执行步骤2
        SeckillUser user = UserUtils.getUser();
        pd.put("goodId", goodId);
        pd.put("userId", user.getId());
        pd = seckillOrderService.findByGoodIdAndUserId(pd);
        if (pd != null) {
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }

//        2.查看map中记录该gooId是否空仓，空仓，则返回秒杀完毕，反之，则执行步骤3
        Map<Long, Boolean> localOverMap = MapUtil.getInstance();
        boolean over = localOverMap.get(goodId);
        if (over) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }

//      3.redis预减库存
        int stock = seckillGoodsService.reduceStock(goodId);
        if (stock < 0) {
            localOverMap.put(goodId, true);
            return Result.error(CodeMsg.SECKILL_OVER);
        }

//      4.把消息加入秒杀队列
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setGoodsId(goodId);
        seckillMessage.setSeckillUser(user);
        mqSender.sendSeckillMessage(seckillMessage);
//        排队中
        return Result.success(1);

    }
}
