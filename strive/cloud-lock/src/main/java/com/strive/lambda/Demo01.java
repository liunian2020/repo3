package com.strive.lambda;

public class Demo01 {
    public static void main(String[] args) {
        // Lambda表达式
        new Thread(() -> System.out.println(1 + "hello world")).start();

        System.out.println("----------------");

        // 方法体
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(2 + "hello world");
            }
        }).start();
        System.out.println("----------1------");
        new Thread(new Runnable(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(3 + "hello world");
                }
            }
        }).start();
    }
}
