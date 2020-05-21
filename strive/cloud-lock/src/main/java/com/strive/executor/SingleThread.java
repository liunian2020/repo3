package com.strive.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */
public class SingleThread {
    public static void main(String[] args) {
        //结果依次输出，相当于顺序执行各个任务。
        //
        //现行大多数GUI程序都是单线程的。Android中单线程可用于数据库操作，文件操作，应用批量安装，
        // 应用批量删除等不适合并发但可能IO阻塞性及影响UI线程响应的操作。
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        //Thread.sleep(2000);
                    } catch (Exception e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
