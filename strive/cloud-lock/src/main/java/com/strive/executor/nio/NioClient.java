package com.strive.executor.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
    private static final String host = "127.0.0.1";
    private static final int port = 8080;
    private Selector selector;

    public static void main(String[] args){
        for (int i=0;i<3;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NioClient client = new NioClient();
                    client.connect(host, port);
                    client.listen();
                }
            }).start();
        }
    }

    public void connect(String host, int port) {
        try {
            SocketChannel sc = SocketChannel.open();//创建通道
            sc.configureBlocking(false);//设置非阻塞
            this.selector = Selector.open();//打开selector
            sc.register(selector, SelectionKey.OP_CONNECT);//注册通道，通过selecter管理监听
            sc.connect(new InetSocketAddress(host, port));//连接
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {
            try {
                int events = selector.select();//阻塞通道，返回可以使用的通道数量
                if (events > 0) {
                    //获得可以使用的keys数组
                    Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                    while (selectionKeys.hasNext()) {//便利key
                        SelectionKey selectionKey = selectionKeys.next();
                        selectionKeys.remove();//移除keys，不会自动移除，只能自己手动移除，防止重复浪费
                        //连接事件
                        if (selectionKey.isConnectable()) {
                            //获得当前通道
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            //判断连接状态
                            if (socketChannel.isConnectionPending()) {
                                socketChannel.finishConnect();//判断是否连接，如果没链接抛异常
                            }
                            //设置非阻塞
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);//注册设置状态：读状态失败
                            //写入向服务端发送数据
                            socketChannel.write(ByteBuffer.wrap(("Hello this is " + Thread.currentThread().getName()).getBytes()));
                        } else if (selectionKey.isReadable()) {//当前状态是读取状态，说明已经和服务端创建连接
                            SocketChannel sc = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            sc.read(buffer);
                            buffer.flip();//获取数据前整理
                            System.out.println("收到服务端的数据："+new String(buffer.array()));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
