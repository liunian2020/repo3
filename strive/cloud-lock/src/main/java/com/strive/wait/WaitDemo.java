package com.strive.wait;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用三个线程按顺序循环打印 abc 三个字母，比如 abcabcabc
 */
public class WaitDemo {
    static Object syn = new Object();

    public String count ="a";
    public static void main(String[] args) {
        new WaitDemo().print();
    }



    private void print(){
        ExecutorService service =  Executors.newFixedThreadPool(3);
        service.execute(new ADemo());
        service.execute(new BDemo());
        service.execute(new CDemo());

    }

    class ADemo implements Runnable{

        @Override
        public void run() {
            while (true){
                synchronized (syn){
                    while(!"a".equals(count)){
                        try {
                            syn.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("a");
                    count = "b";
                    syn.notifyAll();
                }
            }
        }
    }
    class BDemo implements Runnable{

        @Override
        public void run() {
            while (true){
                synchronized (syn){
                    while(!"b".equals(count)){
                        try {
                            syn.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("b");
                    count = "c";
                    syn.notifyAll();
                }
            }
        }
    }
    class CDemo implements Runnable{

        @Override
        public void run() {
            while (true){
                synchronized (syn){
                    while(!"c".equals(count)){
                        try {
                            syn.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("c");
                    count = "a";
                    syn.notifyAll();
                }
            }
        }
    }
}
