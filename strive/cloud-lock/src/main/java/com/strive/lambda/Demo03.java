package com.strive.lambda;

import java.util.function.Function;

/**
 * 从jdk1.8开始为了方便用户开发专门提供了一个新的包：java.util.function
 *
 * 在这个包里面针对于用户有可能做的函数式接口做了一个公共定义
 *
 * 最为核心的有四个接口：
 *
 * a.功能性接口：Function<T, R>
 *
 * 有输入参数，有返回值
 *
 * 是对接收一个T类型参数，返回R类型的结果的方法的抽象
 *
 * 通过调用apply方法执行内容
 *
 * 需求：给定一个字符串，返回字符串长度
 * ————————————————
 */
public class Demo03 {
    public static void main(String[] args) {
        // 定义字符串
        String str = "helloworld";

        // 调用方法
        // 在调用的时候写方法体，方法比较灵活
        int length = testFun(str, (t) -> t.length());

        System.out.println(length);
    }

    // 方法
    /**
     *
     * @param str 输入参数
     * @param fun 表达式 String 为输入类型，Integer为输出类型
     * @return 返回字符串长度
     */
    public static int testFun(String str, Function<String, Integer> fun) {
        // 执行
        Integer length = fun.apply(str);

        return length;
    }

}
