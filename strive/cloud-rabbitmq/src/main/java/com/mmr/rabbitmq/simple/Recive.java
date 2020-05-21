package com.mmr.rabbitmq.simple;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取消息
 */
public class Recive {

    private static final String QUEUE_NAME= "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false, false,false,null);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("resv:"+msg);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }
}
