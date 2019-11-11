package com.webnest.internship.dao;

import com.webnest.internship.bean.StuAchievement;
import com.webnest.internship.bean.StuAchievementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StuAchievementMapper {
    long countByExample(StuAchievementExample example);

    int deleteByExample(StuAchievementExample example);

    int deleteByPrimaryKey(Integer applyId);

    int insert(StuAchievement record);

    int insertSelective(StuAchievement record);

    List<StuAchievement> selectByExample(StuAchievementExample example);

    StuAchievement selectByPrimaryKey(Integer applyId);

    int updateByExampleSelective(@Param("record") StuAchievement record, @Param("example") StuAchievementExample example);

    int updateByExample(@Param("record") StuAchievement record, @Param("example") StuAchievementExample example);

    int updateByPrimaryKeySelective(StuAchievement record);

    int updateByPrimaryKey(StuAchievement record);
}