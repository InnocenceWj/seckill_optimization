package com.wj.utils;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * @author ShallowAn
 */
public class UUIDUtil {
    public static int Guid = 100;

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getGuid() {
        UUIDUtil.Guid += 1;
        long now = System.currentTimeMillis();
        //获取4位年份数字
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        //获取时间戳
        String time = dateFormat.format(now);
        String info = now + "";
        //获取三位随机数
        //int ran=(int) ((Math.random()*9+1)*100);
        //要是一段时间内的数据连过大会有重复的情况，所以做以下修改
        int ran = 0;
        if (UUIDUtil.Guid > 999) {
            UUIDUtil.Guid = 100;
        }
        ran = UUIDUtil.Guid;
        return time + info.substring(2, info.length()) + ran;
    }
}
