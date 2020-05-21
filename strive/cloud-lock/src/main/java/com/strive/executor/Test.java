package com.strive.executor;

public class Test {
    public static <T> T getContent(Class<T> classz) throws IllegalAccessException, InstantiationException {
        return classz.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        //A a = getContent(A.class);
       // B b = getContent(B.class);
        System.out.println("1 << 4:"+(1 << 4));
    }
}
class A{

}
class B{

}
