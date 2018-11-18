package com.wj.service;

import com.wj.entity.SeckillUser;
import com.wj.utils.PageData;

import java.awt.image.BufferedImage;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
public interface SeckillGoodsService {
    BufferedImage createVerifyCode(String token, long goodsId);

    boolean checkVerfyCode(PageData pd);

    String createPath(PageData pd, long goodId);

    boolean checkPath(PageData pd, long goodId);

    int reduceStock(long goodId);

    PageData getStockById(long goodId);
}
