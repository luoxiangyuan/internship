package com.webnest.internship.service;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.dao.InternshipDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * 实训类型service
 * 用于进行实训的各种操作
 * */
@Service
public class InternshipService {

    @Autowired
    InternshipDetailMapper internshipDetailMapper;

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
    public List<InternshipDetail> getList(int EnterpriseId) {
        return internshipDetailMapper.selectByEntId(EnterpriseId);
    }

    //根据企业id和审核状态查询企业的所有实训
    public List<InternshipDetail> getListBySta(int EnterpriseId, int status) {
        return internshipDetailMapper.selectByEntIdAndSta(EnterpriseId, status);
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
        map1.put("submit_num", submitNum);
        map1.put("check_num", checkNum);
        return map1;
    }
}

