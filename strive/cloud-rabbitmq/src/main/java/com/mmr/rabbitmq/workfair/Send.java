package com.mmr.rabbitmq.workfair;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *            |--->C1
 * P--->Queue-|
 *            |--->C2
 * 公平分发 basitQoc（1）
 * */
public class Send {
    private static  final  String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        //将第二个参数从false改成ture 是一个不可以的，因为已经定义了test_simple_queue 这个queue是
        //未持久化的，rabbitMq不允许重新定义一个不同参数的队列,删除rabbitmq中的队列将可以实现持久化
        boolean durable= true;
        //channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        //每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
        //限制发送给同一个消费者，不得超过一条信息
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        //发送消息
        for (int i =0; i <50;i++){
            String msg ="hello+"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i+20);

        }
        channel.close();
        connection.close();
    }
}
