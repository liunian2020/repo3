package com.mmr.rabbitmq.work;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *            |--->C1
 * P--->Queue-|
 *            |--->C2
 * 默认轮询分发策略模式
 * */
public class Send {
    private static  final  String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
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
