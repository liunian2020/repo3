package com.strive.springcloud.controller;

import com.strive.springcloud.common.response.ResponseResult;
import com.strive.springcloud.entities.UserScore;
import com.strive.springcloud.lb.LoadBalancer;
import com.strive.springcloud.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
//@Scope("prototype")多例模式
public class OrderController {
    public  static final String PARMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;
    @RequestMapping("/comsumer/create")
    public ResponseResult<Object> create(UserScore userScore) throws Exception{

       return restTemplate.postForObject(PARMENT_URL+"/create",userScore,ResponseResult.class);
    }
    @RequestMapping("/comsumer/getUser")
    public ResponseResult<Object> getUser() throws Exception{

        return restTemplate.getForObject(PARMENT_URL+"/getUser",null,ResponseResult.class);
    }
    @RequestMapping("/comsumer/getForentityUser2")
    public ResponseResult<Object> getUser2() throws Exception{
        ResponseEntity<ResponseResult> forEntity = restTemplate.getForEntity(PARMENT_URL + "/getUser", ResponseResult.class);
        if (forEntity.getStatusCode().is2xxSuccessful()) {//成功200
            return forEntity.getBody();
        }else
            return ResponseUtil.error(-1,"操作失败");
    }
    @RequestMapping("/comsumer/lb")
    public ResponseResult<Object> getLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() == 0){
            return ResponseUtil.error(-1,"错误");
        }
        ServiceInstance  serviceInstance= loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/lb",ResponseResult.class);
    }
    //private ThreadLocal<Integer> num = new ThreadLocal<Integer>();
    private int num ;
    @RequestMapping("/testScope")
    public void testScope() {
//        num.set(+1);
//        System.out.println(num.get());
        System.out.println(++num);
    }

    @RequestMapping("/testScope2")
    public void testScope2() {
        //        num.set(+1);
//        System.out.println(num.get());
        System.out.println(++num);
    }

}
