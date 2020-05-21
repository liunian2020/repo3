package com.strive.springcloud.services;

import com.strive.springcloud.entities.UserScore;

import java.util.List;

public interface UserScoreServices {
    void insertUserScore(UserScore userScore) throws  Exception;
    List<UserScore> getUserScoreList(UserScore userScore)throws  Exception;
}
