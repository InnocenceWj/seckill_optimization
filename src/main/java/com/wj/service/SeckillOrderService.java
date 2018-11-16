package com.wj.service;

import com.wj.utils.PageData;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
public interface SeckillOrderService {

    PageData findByGoodIdAndUserId(long goodId, long userId);
}
