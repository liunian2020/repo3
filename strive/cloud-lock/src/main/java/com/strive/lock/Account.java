package com.strive.lock;

/**
 *
 */
public class Account {
    private int id = 0;
    private String name = "";
    private double balance;

    public void transfer(Account from, Account to, double money){
        if(from.getId() > to.getId()){
            System.out.println("run transfer----from >to----");
            synchronized(from){
                System.out.println("from lock --from >to----");
                synchronized(to){
                    // transfer
                    System.out.println("to lock -from >to-----");
                }
            }
        }else{
            System.out.println("run transfer----from <to----");
            synchronized(to){
                System.out.println("to lock --from <to----");
                synchronized(from){
                    // transfer
                    System.out.println("from lock -from <to-----");
                }
            }
        }
    }

    public int getId() {
        return id;
    }
}
