package com.strive.springcloud.controller;
import org.springframework.beans.factory.annotation.Value;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String port;
    @RequestMapping("/paymetzk")
    public String paymetzk(){
        return "springcloud zookeeper:"+port;
    }
}
