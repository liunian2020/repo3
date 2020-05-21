package com.mmr.rabbitmq.simple;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static  final  String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        //获取一个通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //创建队列声明
        String msg ="hello simple";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        channel.close();
        connection.close();
    }

}
