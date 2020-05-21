package com.strive.springcloud.service;

import com.strive.springcloud.service.Impl.ConsumerHystrixServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = ConsumerHystrixServiceImpl.class)
public interface ConsumerHystrixService {

    @GetMapping("/hystrix/ok/{id}")
    public String paymentinfo_ok(@PathVariable("id") Integer id);
    @GetMapping("/hystrix/timeout/{id}")
    public String paymentinfotimeout(@PathVariable("id") Integer id);
}
