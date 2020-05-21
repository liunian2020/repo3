package com.strive.lock;

/**
 * 申请锁时发生了交叉闭环申请
 * 线程1锁住A，在未释放A的情况下去申请锁B ,这时线程2已经申请锁B ，在未释放锁B之前去申请锁A,
 * 因此闭环发生，陷入死锁
 */
public class Client {
    public final static String obj1 = "obj1";
    public final static String obj2 = "obj2";

    public static void main(String[] args) {
        DeadLockA deadLockA = new DeadLockA();
        DeadLockB deadLockB = new DeadLockB();
        deadLockA.start();
        deadLockB.start();
    }
}
