package com.strive.lock2;

public class Client {
    public static void main(String[] ars) {
        TestClass classA = new TestClass();
        TestClass classB = new TestClass();
        new TestLock(classA, classB).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new TestLock(classB, classA).start();
    }
}

