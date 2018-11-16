package com.wj.utils;

import com.wj.entity.Good;
import com.wj.redis.RedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * @创建人 wj
 * @创建时间 2018/11/14
 * @描述
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/spring-redis.xml"})
public class RedisUtilTest {
    @Resource
    RedisDao redisDao;

    @Test
    public void testRedis() {
        int a=2;
        boolean flag = redisDao.setObj("wj",123L,a,100L);
        if(flag){
            int str=redisDao.getObj("wj",123L,Integer.class);
            System.out.println(str);
        }
    }

}