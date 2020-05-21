package com.strive.lock3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁：指该锁一次只能被一个线程所持有。对ReentrantLock和synchronized来说，它们都是独占锁。
 *
 * 共享锁：指该锁可以被多个线程所持有。
 *
 * 对ReentrantReadWriteLock来说，读锁是共享锁，写锁是独占锁。
 *
 * 读锁的共享锁可以保证并发读是非常高效的，读写，写读，写写的过程是互斥的。
 *
 * 　　也就是说，多个线程同时读取一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。但是，如果有一个线程想去写共享资源，就不应该又其他的线程对资源进行读或者写。
 *
 * 总结：读-读可共存
 *
 * 　　   读-写不可以共存
 *
 * 　　　写-写不可以共存
 */
public class CacheResource {
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value){
        try {
            reentrantReadWriteLock.writeLock().lock();
            System.out.println("--------------------------------------------------------------");
            System.out.println(Thread.currentThread().getId() + " \t " + "正在写入" + key);

        }finally {
            System.out.println(Thread.currentThread().getId() + " \t " + "写入成功" + key);
            System.out.println("--------------------------------------------------------------");

            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        try {
            reentrantReadWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getId() + " \t " + "正在读取 \t" + key);
        }finally {
            System.out.println(Thread.currentThread().getId() + " \t " + "读取成功 \t" + key);
            reentrantReadWriteLock.readLock().unlock();
        }

    }
}
