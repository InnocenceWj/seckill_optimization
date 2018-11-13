package com.wj.amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/spring-rabbitmq.xml"})
public class MQSenderTest {

    @Resource
    MQSender sender;

    @Test
    public void testMessage() {
//        自己new MQSender，amqpTemplate不会被注入
//        MQSender sender = new MQSender();
        SeckillMessage message = new SeckillMessage();
        message.setGoodsId(123L);
        sender.sendSeckillMessage(message);
    }
}