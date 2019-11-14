package com.webnest.internship.service;

import com.webnest.internship.bean.Enterprise;
import com.webnest.internship.dao.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseService {

    @Autowired
    EnterpriseMapper enterpriseMapper;

    //根据公司id，获取公司信息
    public Enterprise getEntprise(int id){
        return enterpriseMapper.selectByPrimaryKey(id);
    }
}
