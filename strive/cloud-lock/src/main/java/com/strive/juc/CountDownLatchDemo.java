package com.strive.juc;

import java.util.concurrent.CountDownLatch;

/**
 * ##CountDownLatch—闭锁
 *
 * 使用CountDownLatch可以实现类似多线程下计数器的功能。
 *
 * 构造器：
 *
 * 1.参数count为计数器
 *
 * 2.调用await()方法时，线程被挂起，它会等待直到count值为0才继续执行
 *
 * ​	重载：public boolean await(long await ,TimeUnit unit)throw InterruptedException{}//等到一定时间count还没有减到0，继续执行。
 */
public class CountDownLatchDemo implements Runnable{

    private CountDownLatch countDownLatch;
    CountDownLatchDemo(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    //这里要使用同步synchronized
    public void run(){
        try {
            //Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"到达终点");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        System.out.println("所有线程开始执行任务....");
        for (int i = 0; i < 10; i++) {
            new Thread(new CountDownLatchDemo(countDownLatch),"运动员"+(i+1)).start();
        }

        //调用await()方法阻塞当前线程，当所有子线程执行完毕，主线程恢复执行
        countDownLatch.await();
        System.out.println("所有线程完成任务，可以开始做其它想做的事 了....");

    }
}