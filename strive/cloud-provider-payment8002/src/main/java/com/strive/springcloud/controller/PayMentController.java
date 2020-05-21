package com.strive.springcloud.controller;

import com.strive.springcloud.common.response.ResponseResult;
import com.strive.springcloud.entities.UserScore;
import com.strive.springcloud.services.UserScoreServices;
import com.strive.springcloud.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PayMentController {
    @Autowired
    private UserScoreServices userScoreServices;
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
    @RequestMapping("/lb")
    public ResponseResult<Object> payMnentLb() throws Exception {
        return ResponseUtil.success("端口号"+port);
    }
}
