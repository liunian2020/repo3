package com.mmr.rabbitmq.workfair;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recive1 {
    private static  final  String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);//保证一次只分发一个
        //获得消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("Recive1 MSG:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //由自动改成手动分发，在队列中手动获取
                    //envelope.getDeliveryTag()标记一个标识，确认已经收到了
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //监听
        boolean autoAsk =  false;//自动应答
        channel.basicConsume("",autoAsk,defaultConsumer);
    }
}
