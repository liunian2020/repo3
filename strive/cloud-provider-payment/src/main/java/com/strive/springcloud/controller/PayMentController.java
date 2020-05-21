package com.strive.springcloud.controller;

import com.strive.springcloud.common.response.ResponseResult;
import com.strive.springcloud.entities.UserScore;
import com.strive.springcloud.services.UserScoreServices;
import com.strive.springcloud.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PayMentController {
    private static Logger log = LoggerFactory.getLogger(PayMentController.class);

    @Autowired
    private UserScoreServices userScoreServices;

    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${server.port}")
    String port;
    @RequestMapping("/getUser")
    public ResponseResult<Object> getUser() throws Exception {
        UserScore userScore = new UserScore();
        List<UserScore> userScoreList = userScoreServices.getUserScoreList(userScore);

        return ResponseUtil.success(port);
    }
    @RequestMapping("/create")
    public ResponseResult<Object> create(@RequestBody UserScore userScore) throws Exception {
        userScoreServices.insertUserScore(userScore);
        return ResponseUtil.success("添加成功"+port);
    }
    @RequestMapping("/discovery")
    public ResponseResult<Object> discovery() throws Exception {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("********ELENE::"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance service : instances) {
            log.info(service.getHost()+"\t"+service.getServiceId());
        }
        return ResponseUtil.success(instances);
    }
    @RequestMapping("/lb")
    public ResponseResult<Object> payMnentLb() throws Exception {
        return ResponseUtil.success("端口号"+port);
    }
    @RequestMapping("/feign/paymentFeignTimeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return port;
    }
}
