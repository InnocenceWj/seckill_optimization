package com.wj.amqp;


import com.wj.entity.SeckillUser;
import com.wj.exception.GlobalException;
import com.wj.result.CodeMsg;
import com.wj.service.SeckillGoodsService;
import com.wj.service.SeckillOrderService;
import com.wj.utils.PageData;
import com.wj.utils.UserUtils;

import javax.annotation.Resource;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
public class MQReceiver {

    @Resource
    private SeckillGoodsService seckillGoodsService;
    @Resource
    private SeckillOrderService seckillOrderService;


    public void receive(String message) {
        try {
            PageData pd0 = new PageData();
            SeckillMessage seckillMessage = UserUtils.stringToBean(message, SeckillMessage.class);
            long goodId = seckillMessage.getGoodsId();
            SeckillUser seckillUser = seckillMessage.getSeckillUser();
//        判断库存
            PageData pd = seckillGoodsService.getStockById(goodId);
            int stock = (int) pd.get("stock_count");
            if (stock < 1) {
                return;
            }
            pd0.put("stockCount", stock);
//        查看是否有订单,发送消息前，进行查看过，所以无须再查
            pd0.put("userId", seckillUser.getId());
            pd0.put("goodId", goodId);
//        pd = seckillOrderService.findByGoodIdAndUserId(pd0);
//        if (pd != null) {
//            return;
//        }

//        增加订单明细/减库存
            seckillOrderService.excuteSeckill(pd0);

            System.out.println("接收到的消息：" + message);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.RECEIVE_MQ);
        }

    }
}
