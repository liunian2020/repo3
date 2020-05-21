package com.strive.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteCacheDemo {
    private static Map<String, Object> data = new HashMap<>();
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);//非公平的
    private static Lock rlock = lock.readLock();
    private static Lock wlock = lock.writeLock();

    public static Object get(String key) {
        rlock.lock();
        try {
            return data.get(key);
        } finally {
            rlock.unlock();
        }
    }

    public static Object put(String key, Object value) {
        wlock.lock();
        try {
            return data.put(key, value);
        } finally {
            wlock.unlock();
        }
    }
}
