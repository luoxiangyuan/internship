package com.webnest.internship.service;

import com.webnest.internship.bean.Msg;
import com.webnest.internship.bean.StuApply;
import com.webnest.internship.dao.StuApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class ApplyService {
    @Autowired
    StuApplyMapper stuApplyMapper;

    //处理申请
    public void putApp(StuApply stuApply) {
        stuApplyMapper.updateByPrimaryKeySelective(stuApply);
    }

    //根据实训查询所有申请
    public List<Map<String, Object>> getApplyList(int internshipId) {
        return stuApplyMapper.selectByExpId(internshipId);
    }

    //根据实训id和申请状态查询申请
    public List<Map<String, Object>> getApplyListBySta(int internshipId, int status) {
        return stuApplyMapper.selectByExpIdSta(internshipId, status);
    }

    //根据实训id管理学生
    public List<Map<String, Object>> ManageStu(int expId) {
        return stuApplyMapper.selectStu(expId);
    }

    //根据实训查询所有申请
    public List<StuApply> getApplyList1(int internshipId) {
        return stuApplyMapper.getByExpId(internshipId);
    }

    //根据申请id取得申请
    public StuApply getApply(int apply_id) {
        return stuApplyMapper.selectByPrimaryKey(apply_id);
    }
}
