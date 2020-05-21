package com.strive.lock2;

public class TestLock extends Thread {
    private TestClass class1;
    private TestClass class2;

    public TestLock(TestClass class1, TestClass class2) {
        this.class1 = class1;
        this.class2 = class2;
    }

    @Override
    public void run() {
        class1.method(class2);
    }
}

