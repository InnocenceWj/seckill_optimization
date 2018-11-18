package com.wj.amqp;


import com.wj.entity.SeckillUser;
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
        SeckillMessage seckillMessage = UserUtils.stringToBean(message, SeckillMessage.class);
        long goodId = seckillMessage.getGoodsId();
        SeckillUser seckillUser = seckillMessage.getSeckillUser();
//        判断库存
        PageData pd = seckillGoodsService.getStockById(goodId);
        int stock = (int) pd.get("stock_count");
        if (stock < 1) {
            return;
        }
//        查看是否有订单
        pd.put("userId", seckillUser.getId());
        pd.put("goodId", goodId);
        pd = seckillOrderService.findByGoodIdAndUserId(pd);
        if (pd != null) {
            return;
        }

//        增加订单明细/减库存
        seckillOrderService.excuteSeckill(pd);

        System.out.println("接收到的消息：" + message);
    }
}
