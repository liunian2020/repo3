package com.strive.lock3;

public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        CacheResource resource = new CacheResource();

        for (int i = 0; i < 5; i++){
            final int tmp = i;
            new Thread(()->{
                resource.put(tmp + "", "");
            }).start();
        }

        for (int i = 0; i < 5; i++){
            final int tmp = i;
            new Thread(()->{
                resource.get(tmp + "");
            }).start();
        }
    }
}
