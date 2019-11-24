package com.webnest.internship.service;

import com.webnest.internship.bean.Admin;
import com.webnest.internship.bean.StuAchievement;
import com.webnest.internship.dao.AdminMapper;
import com.webnest.internship.dao.StuAchievementMapper;
import com.webnest.internship.dao.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StuAchievementMapper stuAchievementMapper;

    //登录
    public Admin login(String email, String password) {
        return adminMapper.login(email, password);
    }

    //按年级管理学生
    public List<Map<String, Object>> stuManage(String grade) {
        String like = grade + "%";
        List<Map<String, Object>> list = studentMapper.selectByGrade(like);
        List<Map<String, Object>> list1 = studentMapper.selectApplyIdByGrade(like);
        for (Map<String, Object> map1 : list){
            for (Map<String, Object> map : list1) {
                if (!map.get("stu_id").equals(map1.get("stu_id"))) {
                    map1.put("mark", null);
                    map1.put("work", null);
                    map1.put("certificate", null);
                    map1.put("perception", null);
                } else {
                    int applyId = (int) map.get("apply_id");
                    StuAchievement achievement = stuAchievementMapper.selectByPrimaryKey(applyId);
                    map1.put("mark", achievement.getMark());
                    map1.put("work", achievement.getWorkUrl());
                    map1.put("certificate", achievement.getCertificate());
                    map1.put("perception", achievement.getPerception());
                }
            }
        }
        return list;
    }
}
