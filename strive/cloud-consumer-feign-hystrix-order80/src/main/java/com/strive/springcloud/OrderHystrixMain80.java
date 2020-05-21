package com.strive.springcloud;

import com.strive.springcloud.test.SayDemo;
import com.strive.springcloud.test.SpringBeanInfo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
//@EnableFeignClients
//客户端调用方法
//@EnableHystrix
public class OrderHystrixMain80 {
    public static void main(String[] args) {
        //ClassPathXmlApplicationContext classPathXmlApplicationContext = null;
        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext(SpringBeanInfo.class);
        SayDemo sayDemo = (SayDemo)annotationConfigApplicationContext.getBean("sayDemo");
        sayDemo.setName("张三");
        sayDemo.SayShow();
        //SpringApplication.run(OrderHystrixMain80.class,args);
    }
}
