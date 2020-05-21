package com.mmr.rabbitmq.publicSub;

import com.mmr.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PublicRecive1 {
    private static  final  String QUEUE_NAME = "test_queue_email_fanout";
    private static  final  String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

//        channel.basicQos(1);//保证一次只分发一个
        //获得消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("Recive1 fanout MSG:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //由自动改成手动分发，在队列中手动获取
                    //envelope.getDeliveryTag()标记一个标识，确认已经收到了
//                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //监听
        boolean autoAsk =  true;//自动应答
        channel.basicConsume("",autoAsk,defaultConsumer);
    }
}
