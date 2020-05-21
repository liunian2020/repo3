package com.strive.spingcloud.controller;

import com.strive.spingcloud.service.ConsumerFeignService;
import com.strive.springcloud.common.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderFeignController {
    @Resource
    private ConsumerFeignService consumerFeignService;

    @RequestMapping("/feign/getUser")
    public ResponseResult<Object> getUser() throws Exception {
        return consumerFeignService.getUser();
    }

    @RequestMapping("/consumer/feign/paymentFeignTimeout")
    public String paymentFeignTimeout(){
       return consumerFeignService.paymentFeignTimeout();
    }
}
