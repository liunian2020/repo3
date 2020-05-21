package com.strive.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 对于循环等待的情况，可以引入变量Thread.currentThread().isInterrupted()来作为其中的一个判断条件。
 * isInterrupted()方法返回当前线程是否有被 interrupt。
 * shutdownNow()的内部实现实际上就是通过 interrupt 来终止线程，所以当调用shutdownNow()时，isInterrupted()会返回true。
 * 此时就可以跳出循环等待。
 * 先调用shutdown()使线程池状态改变为SHUTDOWN，线程池不允许继续添加线程，并且等待正在执行的线程返回。
 * 调用awaitTermination设置定时任务，代码内的意思为 2s 后检测线程池内的线程是否均执行完毕（就像老师告诉学生，“最后给你 2s 钟时间把作业写完”），若没有执行完毕，则调用shutdownNow()方法。
 */
public class ExecutorServiceDemo5 {
    static Runnable run = () -> {
        long num = 0;
        boolean flag = true;
        while (flag && !Thread.currentThread().isInterrupted()) {
            num += 1;
            if (num == Long.MAX_VALUE) {
                flag = false;
            }
        }
        System.out.println(num);
    };

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(run);
        service.shutdown();
        try {
            if (!service.awaitTermination(2, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
        }
    }
}
