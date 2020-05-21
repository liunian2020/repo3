package com.strive.springcloud.services.Impl;

import com.strive.springcloud.entities.UserScore;
import com.strive.springcloud.mapper.UserScoreMapper;
import com.strive.springcloud.services.UserScoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserScoreServicesImpl implements UserScoreServices {

    @Autowired
    private UserScoreMapper userScoreMapper;

    @Override
    public void insertUserScore(UserScore userScore) throws Exception {
        userScoreMapper.insertUserScore(userScore);
    }

    @Override
    public List<UserScore> getUserScoreList(UserScore userScore) throws Exception {
        return userScoreMapper.searchUserScoreList(userScore);
    }
}
