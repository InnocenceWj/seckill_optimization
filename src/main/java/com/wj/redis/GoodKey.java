package com.wj.redis;

/**
 * @创建人 wj
 * @创建时间 2018/11/14
 * @描述
 */
public class GoodKey extends BasePrefix {
    public static GoodKey goodsList = new GoodKey(60, "gl");
    public static GoodKey goodsDetail = new GoodKey(60, "gd");
    public static GoodKey seckillGoodsStock = new GoodKey(0, "gs");
    private GoodKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
