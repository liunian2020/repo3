package com.strive.lock;

public class ThreadRunableDemo {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(""+Thread.currentThread().getName());
            }
        };
        //Thread h = null;
        for (int i = 0; i < 5; i++) {
            new Thread(r,"线程"+i).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------");
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("555555"+Thread.currentThread().getName());
                }
            }, "线程" + i).start();
        }
    }
}
