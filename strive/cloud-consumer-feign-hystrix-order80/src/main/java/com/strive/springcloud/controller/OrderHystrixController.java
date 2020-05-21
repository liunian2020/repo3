package com.strive.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.strive.springcloud.service.ConsumerHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderHystrixController {
    @Resource
    private ConsumerHystrixService ConsumerHystrixService;
    @GetMapping("/consumer/hystrix/ok/{id}")
    public String paymentinfo_ok(@PathVariable("id") Integer id){
        String result = ConsumerHystrixService.paymentinfo_ok(id);
        return  result;
    }
//    @GetMapping("/consumer/hystrix/timeout/{id}")
//    public String paymentinfotimeout(@PathVariable("id") Integer id){
//        String result = ConsumerHystrixService.paymentinfotimeout(id);
//        System.out.println("result:::"+result);
//        return  result;
//    }
    //参数在这个HystrixPropertiesManager类中
    //客户端降级
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
            //这个方法超时时间是3秒钟
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "6000")
    })
    @GetMapping("/consumer/hystrix/timeout/{id}")
    public String paymentInfoTimeOut(@PathVariable("id") Integer id){
        return ConsumerHystrixService.paymentinfotimeout(id);
    }
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){

        return "我是消费者80："+Thread.currentThread().getName()+"paymentTimeOutFallbackMethod,请重新操作";
    }
}
