package com.strive.executor.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端
 */
public class TimeClient {
    public static void main(String[] args){
        int port=9001;

        if(args!=null&&args.length>0){

            port=Integer.parseInt(args[0]);
        }


        BufferedReader in=null;

        PrintWriter out=null;

        try {
            try(Socket socket=new Socket("127.0.0.1",port)){

                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out=new PrintWriter(socket.getOutputStream(),true);

                out.println("Query Time Order");
                System.out.println("Send order to server succeed.");
                String resp=in.readLine();

                System.out.println("Now the time is: "+resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
