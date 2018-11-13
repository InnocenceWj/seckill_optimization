package com.wj.amqp;

import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/spring-rabbitmq.xml"})
@Component
public class Test {
    @Autowired
    AmqpTemplate amqpTemplate;

    public Test() {
        System.out.println("constructer: amqpTemplate"+ Objects.isNull(amqpTemplate)+"#############");
    }

    @PostConstruct  //该注解的意思是会在Autowired注入之后执行
    public  void init(){
        System.out.println("init:amqpTemplate"+ Objects.isNull(amqpTemplate)+"#############");
    }

    public void send(){ //外部调用的方法
        System.out.println("send: amqpTemplate"+Objects.isNull(amqpTemplate)+"#############");
    }

    @org.junit.Test
    public void testMethod(){
        Test test = new Test(); //通过自行new，而不是在Controller中@Autowired来修饰
        test.send();
    }

}
