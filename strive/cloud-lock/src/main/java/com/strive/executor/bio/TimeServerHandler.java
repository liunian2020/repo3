package com.strive.executor.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable{
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter out = null;

        try {
            while (true) {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                String currentTime = "";
                String body = null;
                body = bufferedReader.readLine();//如果没有消息就会返回NULL 直接结束循环
                if (body == null)
                    break;
                currentTime = "query time order".equalsIgnoreCase(body) ?
                       " 成功了☺"+ new Date(System.currentTimeMillis()).toString() : "bad order";
                System.out.println("服务端接收到 query time order 命令 " + currentTime);
                out.println(currentTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
