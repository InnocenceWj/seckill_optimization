package com.wj.service.impl;

import com.wj.mapper.SeckillOrderMapper;
import com.wj.service.SeckillOrderService;
import com.wj.utils.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService{

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    public PageData findByGoodIdAndUserId(long goodId, long userId) {
        return seckillOrderMapper.findByGoodIdAndUserId(goodId,userId);
    }
}
