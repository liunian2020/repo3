package com.mmr.rabbitmq.workfair;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recive2 {

    private static  final  String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        //声明队列
        //将第二个参数从false改成ture 是一个不可以的，因为已经定义了test_simple_queue 这个queue是
        //未持久化的，rabbitMq不允许重新定义一个不同参数的队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);//保证一次只分发一个
        //获得消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("Recive2 MSG:"+msg);
                try {
                    Thread.sleep(1000);
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
        //boolean autoAsk =  true;自动确认模式，一旦rabbitmq将消息分发给消费者，就会从内存中删除
        //这种情况如果杀死正在执行的消费者，就会丢失正在处理的消息
        boolean autoAsk =  false;//手动模式，如果有一个消费者挂掉，就会交付给其他消费者，
        //rabbitmq支持消息应答，消费者发送一个消息应答，rabbitmq这个消息已经处理完成，就可以删除，
        // 然后rabbit删除内存中的消息

        channel.basicConsume("",autoAsk,defaultConsumer);
    }
}
