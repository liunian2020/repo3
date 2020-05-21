package com.strive.springcloud.controller;

import com.strive.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @Value("${server.port")
    public String serverPort;
    @GetMapping("/hystrix/ok/{id}")
    //@MyAnnotation(methodName = "paymentinfo_ok")
    public String paymentinfo_ok(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfoOk(id);
        return  result;
    }
    @GetMapping("/hystrix/timeout/{id}")
    public String paymentinfotimeout(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfoTimeOut(id);
        System.out.println("result:::"+result);
        return  result;
    }
    @GetMapping("/hystrix/circuitBreaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        System.out.println("result:::"+result);
        return  result;
    }
}
