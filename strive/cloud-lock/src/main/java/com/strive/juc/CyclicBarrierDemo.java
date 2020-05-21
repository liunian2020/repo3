package com.strive.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * ##CyclicBarrier线程屏障的功能
 *
 * 通过它，可以让一组线程等待至某个状态之后再全部执行。叫做回环是因为当所有线程都被释放后
 * 应用场景：子线程在往主内存写数据
 * 可以协同多个线程，让多个线程在这个屏障前等到，直到所有线程都到达了这个屏障时，再一起执行后面的操作。
 * 参数parties指让多少个线程或者任务等待至barrier状态；参数barrierAction为当这些线程都达到barrier状态时会恢复执行，恢复执行之前随机挑选一个执行任务。
 */
public class CyclicBarrierDemo implements Runnable{
    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierDemo(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"正在写作");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"写完");
            //相当于一面墙,每调用一次await()方法计数器-1，当为0时所有线程恢复执行
            cyclicBarrier.await();
            System.out.println("出版");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(5);//所有线程到达终点的个数
        for (int i=0;i<5;i++){
            new Thread(new CyclicBarrierDemo(cyclicBarrier),"作家"+(i+1)).start();
        }
    }
}
