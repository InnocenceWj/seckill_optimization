package com.wj.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 wj
 * @创建时间 2018/11/16
 * @描述
 */
public class MapUtil {

    //    当redis中商品gooId库存小于0时，false，可以减少对redis的访问
    private static volatile Map<Long, Boolean> localOverMap;

    private MapUtil() {}

    public static Map getInstance() {
        if (localOverMap == null) {
            synchronized (MapUtil.class) {
                if (localOverMap == null) {
                    localOverMap = new HashMap<>();
                }
            }
        }
        return localOverMap;
    }
}
