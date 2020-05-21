package com.mmr.rabbitmq.publicSub;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式  |---->Queue ---->C1
 * p-->X--->|
 *          |---->Queue ---->C2
 */
public class PublicSend {
    private static  final  String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String msg = "hello ps";
        //发送消息
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("11111");
        channel.close();
        connection.close();
    }
}
