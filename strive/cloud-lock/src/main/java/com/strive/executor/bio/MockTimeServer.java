package com.strive.executor.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪NIO 同步非阻塞
 */
public class MockTimeServer {
    public static void main(String[] args){

        int port=9001;
        if(args!=null&&args.length>0){
            port=Integer.parseInt(args[0]);
        }

        ServerSocket server=null;

        try {
            server=new ServerSocket(port);

            System.out.println("The time Server is start in port: "+port);
            Socket socket=null;
            TimeServerHandlerExecutePool executor=new TimeServerHandlerExecutePool(50,10000);
            while(true){
                socket=server.accept();

                executor.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(server!=null){
                System.out.println("The time server close");
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server=null;
            }
        }
    }
}
