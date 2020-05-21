package com.strive.executor.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutePool {

    private ExecutorService executorService;

    //corePoolSize	int	核心线程池大小
    //2	maximumPoolSize	int	最大线程池大小
    //3	keepAliveTime	long	线程最大空闲时间
    //4	unit	TimeUnit	时间单位
    //5	workQueue	BlockingQueue<Runnable>	线程等待队列
    //6	threadFactory	ThreadFactory	线程创建工厂
    //7	handler	RejectedExecutionHandler	拒绝策略
    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        //Runtime.getRuntime().availableProcessors() 处理器的个数
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));

    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }
}