package com.strive.juc;

import java.util.concurrent.Exchanger;

/**
 * 应用场景：一般用于两个线程交换数据；
 *
 * 首先调用exchanger方法的线程会阻塞直到有新的线程进入缓冲区，
 *
 * 交换彼此线程数据再同时恢复执行，
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger exchanger=new Exchanger();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("等待交换数据....");
                try {
                    String str= (String) exchanger.exchange("给你一朵小花花");
                    System.out.println(Thread.currentThread().getName()+" :"+str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"大猪猪");
        thread1.start();
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String string= (String) exchanger.exchange("给你你个大牛牛");
                    System.out.println(Thread.currentThread().getName()+" :"+string);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"大实时");
        thread2.start();
    }
}
