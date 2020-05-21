package com.strive.lock;


import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Lock lock = new ReentrantLock();    //注意这个地方，声明为类的属性
    public static void main(String[] args)  {
        final ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();

        new Thread(){
            public void run() {
                reentrantLockDemo.insert(Thread.currentThread());
            };
        }.start();
        //可以用Java箭头函数特性改写上述冗余代码：
        // new Thread(){()->Thread.currentThread}.start();
        new Thread(){
            public void run() {
                reentrantLockDemo.insert(Thread.currentThread());
            };
        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < reentrantLockDemo.arrayList.size(); i++) {
            System.out.println("I:::"+reentrantLockDemo.arrayList.get(i));
        }
    }

    public void insert(Thread thread) {
        lock.lock();
        try {
            System.out.println(thread.getName()+"得到了锁");
            for(int i=0;i<5;i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally {
            System.out.println(thread.getName()+"释放了锁");
            lock.unlock();
        }
    }
}
