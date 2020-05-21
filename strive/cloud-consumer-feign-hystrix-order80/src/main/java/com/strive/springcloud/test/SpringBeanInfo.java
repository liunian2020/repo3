package com.strive.springcloud.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanInfo {
    @Bean
    public SayDemo sayDemo(){
        SayDemo sayDemo = new SayDemo();
        return sayDemo;
    }
}
