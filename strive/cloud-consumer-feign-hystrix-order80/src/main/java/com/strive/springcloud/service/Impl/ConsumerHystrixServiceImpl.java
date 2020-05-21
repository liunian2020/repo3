package com.strive.springcloud.service.Impl;

import com.strive.springcloud.service.ConsumerHystrixService;
import org.springframework.stereotype.Component;

@Component
public class ConsumerHystrixServiceImpl
        implements ConsumerHystrixService {
    @Override
    public String paymentinfo_ok(Integer id) {
        return "ConsumerHystrixServiceImpl 出现异常";
    }

    @Override
    public String paymentinfotimeout(Integer id) {
        return "paymentinfotimeout 出现异常";
    }
}
