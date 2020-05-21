package com.strive.springcloud.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OwnAspect {
    @Pointcut(value="@annotation(com.strive.springcloud.annotation.MyAnnotation)")
    public void myPointcut(){

    }
    @Before(value="myPointcut()")
    public void processAuthority(){
        System.out.println("执行了processAuthority方法");
    }
}
