package com.webnest.internship.service;

import com.webnest.internship.bean.Enterprise;
import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.dao.EnterpriseMapper;
import com.webnest.internship.dao.InternshipDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/*
 * 实训类型service
 * 用于进行实训的各种操作
 * */
@Service
public class InternshipService {

    @Autowired
    InternshipDetailMapper internshipDetailMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    //根据实训id查询实训
    public InternshipDetail getInternship(int id) {
        return internshipDetailMapper.selectByPrimaryKey(id);
    }

    //根据实训id删除实训
    public void delInternship(int id) {
        internshipDetailMapper.deleteByPrimaryKey(id);
    }

    //根据实训id修改实训信息
    public void updateInternship(InternshipDetail updateInt) {
        internshipDetailMapper.updateByPrimaryKeySelective(updateInt);
    }

    //新增实训信息
    public void createInternship(InternshipDetail newInt) {
        internshipDetailMapper.insertSelective(newInt);
    }

    //根据企业id查询企业的所有实训
    public List<Map<String, Object>> getList(int EnterpriseId) {
        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(EnterpriseId);
        List<Map<String, Object>> list = new ArrayList<>();
        List<InternshipDetail> internshipDetails = internshipDetailMapper.selectByEntId(EnterpriseId);
        for (InternshipDetail internshipDetail : internshipDetails) {
            Map<String, Object> map = new HashMap<>();
            map.put("expId", internshipDetail.getInternshipId());
            map.put("topic", internshipDetail.getTopic());
            map.put("enterprise", enterprise.getName());
            map.put("exp_begin_time", internshipDetail.getExpBeginTime());
            map.put("exp_end_time", internshipDetail.getExpEndTime());
            map.put("submit_time", internshipDetail.getSubmitTime());
            map.put("expEndTime", internshipDetail.getExpEndTime());
            map.put("apply_end_time", internshipDetail.getApplyEndTime());
            map.put("status", internshipDetail.getStatus());
            map.put("submit_num", internshipDetailMapper.getSubmitNum(internshipDetail.getInternshipId()));
            map.put("check_num", internshipDetailMapper.getCheckNum(internshipDetail.getInternshipId()));
            if (!map.isEmpty()) {
                list.add(map);
            }
        }
        return list;
    }

    //根据企业id和审核状态查询企业的所有实训
    public List<Map<String, Object>> getListBySta(int EnterpriseId, int status) {
        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(EnterpriseId);
        List<Map<String, Object>> list = new ArrayList<>();
        List<InternshipDetail> internshipDetails = internshipDetailMapper.selectByEntIdAndSta(EnterpriseId, status);
        for (InternshipDetail internshipDetail : internshipDetails) {
            Map<String, Object> map = new HashMap<>();
            map.put("expId", internshipDetail.getInternshipId());
            map.put("topic", internshipDetail.getTopic());
            map.put("enterprise", enterprise.getName());
            map.put("exp_begin_time", internshipDetail.getExpBeginTime());
            map.put("exp_end_time", internshipDetail.getExpEndTime());
            map.put("submit_time", internshipDetail.getSubmitTime());
            map.put("expEndTime", internshipDetail.getExpEndTime());
            map.put("apply_end_time", internshipDetail.getApplyEndTime());
            map.put("status", internshipDetail.getStatus());
            map.put("submit_num", internshipDetailMapper.getSubmitNum(internshipDetail.getInternshipId()));
            map.put("check_num", internshipDetailMapper.getCheckNum(internshipDetail.getInternshipId()));
            if (!map.isEmpty()) {
                list.add(map);
            }
        }
        return list;
    }

    //返回所有实训信息，并加入申请人数，以及双选人数
    public List<Map<String, Object>> getAllExp() {
        List<Map<String, Object>> list1 = internshipDetailMapper.selectAll();
        for (Map<String, Object> map : list1) {
            int expId = (int) map.get("expId");
            int checkNum = internshipDetailMapper.getCheckNum(expId);
            int submitNum = internshipDetailMapper.getSubmitNum(expId);
            map.put("submit_num", submitNum);
            map.put("check_num", checkNum);
        }
        return list1;
    }

    //返回所有实训信息，并加入申请人数，以及双选人数根据状态
    public List<Map<String, Object>> getAllExpBySta(int status) {
        List<Map<String, Object>> list = internshipDetailMapper.selectAllBySta(status);
        for (Map<String, Object> map : list) {
            if (status == 1) {
                int expId = (int) map.get("expId");
                int checkNum = internshipDetailMapper.getCheckNum(expId);
                int submitNum = internshipDetailMapper.getSubmitNum(expId);
                map.put("submit_num", submitNum);
                map.put("check_num", checkNum);
            }
        }
        return list;
    }

    //返回所有实训信息，并加入申请人数，以及双选人数
    public Map<String, Object> getExp(int expId) {
        Map<String, Object> map1 = internshipDetailMapper.selectByExpId(expId);
        int checkNum = internshipDetailMapper.getCheckNum(expId);
        int submitNum = internshipDetailMapper.getSubmitNum(expId);
        int entCheckNum = internshipDetailMapper.getEntCheckNum(expId);
        map1.put("submit_num", submitNum);
        map1.put("check_num", checkNum);
        map1.put("enterprise_check_num", entCheckNum);
        return map1;
    }
}

