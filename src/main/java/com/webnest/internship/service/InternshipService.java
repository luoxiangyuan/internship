package com.webnest.internship.service;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.dao.InternshipDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 实训类型service
* 用于进行实训的各种操作
* */
@Service
public class InternshipService {

    @Autowired
    InternshipDetailMapper internshipDetailMapper;

    //根据实训id查询实训
    public InternshipDetail getInternship(int id){
        return internshipDetailMapper.selectByPrimaryKey(id);
    }

    //根据实训id删除实训
    public void delInternship(int id){
        internshipDetailMapper.deleteByPrimaryKey(id);
    }

    //根据实训id修改实训信息
    public void updateInternship(InternshipDetail updateInt){
        internshipDetailMapper.updateByPrimaryKeySelective(updateInt);
    }

    //新增实训信息
    public void createInternship(InternshipDetail newInt){
        internshipDetailMapper.insertSelective(newInt);
    }

    //根据企业id查询企业的所有实训
    public List<InternshipDetail> getList(int EnterpriseId){
        return internshipDetailMapper.selectByEntId(EnterpriseId);
    }

    //根据企业id和审核状态查询企业的所有实训
    public List<InternshipDetail> getListBySta(int EnterpriseId, String status){
        return internshipDetailMapper.selectByEntIdAndSta(EnterpriseId,status);
    }


}
