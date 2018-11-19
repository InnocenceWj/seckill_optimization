package com.wj.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@Configuration
public class MQConfig {
    public static final String SECKILL_QUEUE = "seckill_queue";

    public static final String SECKILL_EXCHANGE = "seckill_exchange";

}
