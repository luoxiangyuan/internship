package com.webnest.internship.service;

import com.webnest.internship.bean.StuAchievement;
import com.webnest.internship.dao.StuAchievementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {
    @Autowired
    StuAchievementMapper stuAchievementMapper;

    //判断结业表中的数据是否均已填入
    public boolean checkAchievement(int applyId){
        StuAchievement stuAchievement = stuAchievementMapper.selectByPrimaryKey(applyId);
        if (stuAchievement.getCertificate()!=null //判断成绩表中的数据是否全部传入，若全部传入返回true；
                &&stuAchievement.getMark()!=null
                &&stuAchievement.getPerception()!=null
                &&stuAchievement.getWorkUrl()!=null){
            return true;
        }
        return false;
    }

    //更新成绩表的信息
    public void updateAchievement(StuAchievement stuAchievement){
        stuAchievementMapper.updateByPrimaryKeySelective(stuAchievement);
    }

    //根据id取得成绩
    public StuAchievement getAchievement(int applyId){
        return stuAchievementMapper.selectByPrimaryKey(applyId);
    }
}
