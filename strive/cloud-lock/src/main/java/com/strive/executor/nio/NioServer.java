package com.strive.executor.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO服务器端如何实现非阻塞？
 * 服务器上所有Channel需要向Selector注册，而Selector则负责监视这些Socket的IO状态(观察者)，
 * 当其中任意一个或者多个Channel具有可用的IO操作时，该Selector的select()方法将会返回大于0的整数，
 * 该整数值就表示该Selector上有多少个Channel具有可用的IO操作，
 * 并提供了selectedKeys（）方法来返回这些Channel对应的SelectionKey集合
 * (一个SelectionKey对应一个就绪的通道)。正是通过Selector，使得服务器端只需要不断地调用Selector实例的select()
 * 方法即可知道当前所有Channel是否有需要处理的IO操作。注：java NIO就是多路复用IO，jdk7之后底层是epoll模型。
 */
public class NioServer {
    private int port;
    private Selector selector;
    private ExecutorService service = Executors.newFixedThreadPool(5);//创建线程池策略 每次可以同时对5条线程进行处理

    public static void main(String[] args){
        new NioServer(8080).start();
    }

    public NioServer(int port) {
        this.port = port;
    }

    public void init() {
        ServerSocketChannel ssc = null;
        try {
            //创建通道连接
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);//设置非阻塞状态
            ssc.bind(new InetSocketAddress(port));// 绑定端口
            selector = Selector.open();//选择器开启
            ssc.register(selector, SelectionKey.OP_ACCEPT);//注册将通道channel通过selector管理
            System.out.println("NioServer started ......");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }

    public void accept(SelectionKey key) {
        try {
            //获得当前key的通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();//接受到此通道的连接。如果是非阻塞状态直接连接
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);//设置注册状态，改成读取
            System.out.println("accept a client : " + sc.socket().getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.init();
        while (true) {
            try {
                //阻塞通道，返回可以使用的通道数量
                int events = selector.select();
                if (events > 0) {
                    Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                    while (selectionKeys.hasNext()) {
                        SelectionKey key = selectionKeys.next();
                        selectionKeys.remove();
                        //当前KEY是否连接状态
                        if (key.isAcceptable()) {
                            accept(key);
                        } else {
                            service.submit(new NioServerHandler(key));//返回数据
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class NioServerHandler implements Runnable{

        private SelectionKey selectionKey;

        public NioServerHandler(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            try {
                if (selectionKey.isReadable()) {//判断是否读取状态
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    socketChannel.read(buffer);
                    buffer.flip();
                    System.out.println("收到客户端"+socketChannel.socket().getInetAddress().getHostName()+"的数据："+new String(buffer.array()));
                    //将数据添加到key中
                    ByteBuffer outBuffer = ByteBuffer.wrap(buffer.array());
                    socketChannel.write(outBuffer);// 将消息回送给客户端
                    selectionKey.cancel();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
