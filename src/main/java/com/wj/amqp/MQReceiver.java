package com.wj.amqp;


/**
 * @创建人 wj
 * @创建时间 2018/11/13
 * @描述
 */
public class MQReceiver{

    public  void receive(String message){
        System.out.println("接收到的消息："+message);
    }
}
