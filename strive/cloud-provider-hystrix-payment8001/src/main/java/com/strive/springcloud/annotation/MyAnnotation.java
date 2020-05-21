package com.strive.springcloud.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Target(ElementType.METHOD)//作用于方法
@Documented
public @interface MyAnnotation {
    String methodName () default "";
}
