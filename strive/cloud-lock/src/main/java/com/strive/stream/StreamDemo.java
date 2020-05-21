package com.strive.stream;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDemo {

    /**
     * 校验字符串是否为数字
     * @param s
     * @return
     */
    private static  boolean  isNull(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDefined(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String [] arrayString = {"1","2","bilibili", "of", "codesheep", "5","at", "BILIBILI", "codesheep","23"};
        List<String> list =  java.util.Arrays.asList(arrayString);
       String result = list.stream().filter(i->isNull(i)).filter(i ->i.length()>5)
                .map(i -> i.toLowerCase()).distinct().sorted(Comparator.naturalOrder()).collect
                (  Collectors.joining("❤" ));

// 连词成句来一下，完美！
        System.out.println(result);
    }

}
