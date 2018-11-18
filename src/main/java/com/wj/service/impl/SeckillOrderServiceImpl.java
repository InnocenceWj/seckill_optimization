package com.wj.service.impl;

import com.wj.entity.SeckillGoods;
import com.wj.entity.SeckillOrder;
import com.wj.mapper.SeckillGoodsMapper;
import com.wj.mapper.SeckillOrderMapper;
import com.wj.service.SeckillOrderService;
import com.wj.utils.PageData;
import com.wj.utils.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;
    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public PageData findByGoodIdAndUserId(PageData pd) {
        return seckillOrderMapper.findByGoodIdAndUserId(pd);
    }

    @Override
    @Transactional
    public void excuteSeckill(PageData pd) {
//                减库存
        boolean flag = seckillGoodsMapper.reduceStock(pd);
        if (flag) {
            pd.put("order_id", UUIDUtil.getGuid());
            pd.put("id", UUIDUtil.getGuid());
            seckillOrderMapper.insertOrder(pd);
        }


    }
}
