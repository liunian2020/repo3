package com.strive.lock2;

public class TestClass {
    public synchronized void method(TestClass clazz) {
        System.out.println("TestClass method in");
        synchronized (this){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        clazz.method2();
        System.out.println("TestClass method out");
    }

    public synchronized void method2() {
        System.out.println("TestClass method2");
    }
}
