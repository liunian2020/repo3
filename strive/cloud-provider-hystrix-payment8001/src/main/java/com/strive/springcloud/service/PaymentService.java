package com.strive.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     * 正常访问 ok方法
     * @param id
     * @return
     */
    public String paymentInfoOk(Integer id){
        return "线程池："+Thread.currentThread().getName()+" paymentInfoOk ID:"+id+"正确";
    }
    //,commandProperties = {
    //            @HystrixProperty(name="")
    //    }
    //参数在这个HystrixPropertiesManager类中
    @HystrixCommand(fallbackMethod = "paymentInfoTimeHandler",commandProperties = {
            //这个方法超时时间是3秒钟
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfoTimeOut(Integer id){
        int time =2;
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+" paymentInfoTimeOut ID:"+
                id+"耗时"+time+"秒钟";
    }
    public String paymentInfoTimeHandler(Integer id){

        return "线程池："+Thread.currentThread().getName()+"paymentInfoTimeHandler超时了,请重新操作";
    }
    //=======服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期 在10秒内，请求次数10中有6此失败就跳闸
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60")//失败达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id){
        if (id<0){
            throw new RuntimeException("id不能为负值");
        }
        String uuid = IdUtil.simpleUUID();
        return "线程池："+Thread.currentThread().getName()+" 调用成功 ID:"+uuid;
    }
    public String paymentCircuitBreaker_fallback(Integer id){

        return "id不能为负数,ID："+id;
    }
}
