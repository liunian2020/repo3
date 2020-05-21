package com.strive.lock;

public class DeadLockB extends Thread{
    @Override
    public void run() {
        System.out.println("LockB running");
        while (true){
            synchronized (Client.obj2){
                System.out.println("run B lock obj2");
                System.out.println("LockB trying to lock obj1...");
                synchronized (Client.obj1){
                    System.out.println("run B lock obj1");
                }
            }
        }
    }
}
