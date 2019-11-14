package com.webnest.internship.service;

import com.webnest.internship.bean.StuApply;
import com.webnest.internship.dao.StuApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
    @Autowired
    StuApplyMapper stuApplyMapper;

    //处理申请
    public void putApp(StuApply stuApply){
        stuApplyMapper.updateByPrimaryKeySelective(stuApply);
    }
}
