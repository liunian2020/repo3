package com.strive.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore——信号量：控制某个资源可同时访问的线程个数
 * 应用场景：五个设备，8台生产个工人，工人轮流使用设备。
 * 程执行前用acquire()方法获得信号，执行完通过release()方法归还信号量。如果可用信号为0，acquire就会造成阻塞，
 * 等待release释放信号。作用就是只让指定个数的线程（随机选择或先来后到）并行。
 */
public class SemaphoreDemo implements Runnable{
    private Semaphore s;

    public SemaphoreDemo(Semaphore s) {
        this.s = s;
    }

    @Override
    public void run() {

        try {
            s.acquire();
            System.out.println(Thread.currentThread().getName()+"使用设备");
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName()+"结束");
            s.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        Semaphore s=new Semaphore(5);
        SemaphoreDemo factory=new SemaphoreDemo(s);
        for(int i=0;i<8;i++){
            new Thread(factory).start();
        }

    }
}
