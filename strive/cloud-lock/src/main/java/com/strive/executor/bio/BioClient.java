package com.strive.executor.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BioClient {
    public static void main(String[] args) {
        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1", 10001);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = null;
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("query time order");
            s = in.readLine();
            System.out.println("接收到服务端消息：" + s);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
