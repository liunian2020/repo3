package com.strive.springcloud.mapper;

import com.strive.springcloud.entities.UserScore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserScoreMapper {
    int insertUserScore(UserScore userScore);

    List<UserScore> searchUserScoreList(UserScore userScore);
}
