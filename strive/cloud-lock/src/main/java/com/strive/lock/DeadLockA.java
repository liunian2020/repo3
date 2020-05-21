package com.strive.lock;

public class DeadLockA extends  Thread {

//    ReentrantLock
//            ConcurrentHashMap
    @Override
    public void run() {
        System.out.println("LockA running");
        while(true){
            synchronized (Client.obj1){
                System.out.println("run A lock obj1");
                //获取obj1后先等一会儿，让LockB有足够的时间锁住obj2
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("LockA trying to lock obj2...");
                synchronized (Client.obj2){
                    System.out.println("run A lock obj2");
                }
            }
        }
    }
}
