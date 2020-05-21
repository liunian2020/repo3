package com.strive.spingcloud.service;

import com.strive.springcloud.common.response.ResponseResult;
import com.strive.springcloud.entities.UserScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface ConsumerFeignService {
    @RequestMapping("/getUser")
    public ResponseResult<Object> getUser();

    @RequestMapping("/feign/paymentFeignTimeout")
    public String paymentFeignTimeout();
}
