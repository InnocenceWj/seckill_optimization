package com.wj.amqp;

import com.wj.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@Slf4j
//不加@Service， 报错：NoSuchBeanDefinitionException: No qualifying bean of type 'com.wj.amqp.MQSender'
@Service
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendSeckillMessage(SeckillMessage message) {
        String msg = UserUtils.beanToString(message);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_EXCHANGE, MQConfig.SECKILL_QUEUE, msg);
    }
}
