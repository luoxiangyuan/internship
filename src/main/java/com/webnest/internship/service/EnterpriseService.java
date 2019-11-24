package com.webnest.internship.service;

import com.webnest.internship.bean.Enterprise;
import com.webnest.internship.dao.EnterpriseMapper;
import com.webnest.internship.dao.InternshipDetailMapper;
import com.webnest.internship.dao.StuApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnterpriseService {

    @Autowired
    EnterpriseMapper enterpriseMapper;
    @Autowired
    InternshipDetailMapper internshipDetailMapper;
    @Autowired
    StuApplyMapper stuApplyMapper;

    //根据公司id，获取公司信息
    public Map<String,Object> getEntprise(int id){
        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(id);
        Map<String,Object> ent = new HashMap<String, Object>();
        ent.put("enterpriseId",enterprise.getEnterpriseId());
        ent.put("type",enterprise.getType());
        ent.put("name",enterprise.getName());
        ent.put("address",enterprise.getAddress());
        ent.put("email",enterprise.getEmail());
        ent.put("contact_name",enterprise.getContactName());
        ent.put("contact_tel",enterprise.getContactTel());
        ent.put("intro",enterprise.getIntro());
        ent.put("register_time",enterprise.getRegisterTime());
        ent.put("status",enterprise.getStatus());
        ent.put("images",enterprise.getImages());
        return ent;
    }

    public Enterprise getEnt(int id){
        return enterpriseMapper.selectByPrimaryKey(id);
    }

    //更新公司信息
    public void updateEnt(Enterprise enterprise){
        enterpriseMapper.updateByPrimaryKeySelective(enterprise);
    }

    //取得企业列表
    public List<Enterprise> getAll(){
        return enterpriseMapper.selectAllEnt();
    }

    //根据审核状态取得企业
    public List<Enterprise> getAllBySta(int status){
        return enterpriseMapper.selectAllEntBysta(status);
    }

    //查询申请数目
    public List<Map<String,Object>> countSta(int enterpriseId){
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> expList = internshipDetailMapper.selectExpId(enterpriseId);
        for (Map<String,Object> map : expList){
            Map<String,Object> StaMap = new HashMap<String, Object>();
            int expId = (int) map.get("expId");

            List<Map<String, Object>> staList = stuApplyMapper.countSta(expId);
            if (!staList.isEmpty()){
                StaMap.put("expId",map.get("expId"));
                StaMap.put("enterpriseId",map.get("enterpriseId"));
            }
            for (Map<String,Object> map1 : staList){
                if (map1.get("status").equals(0)){
                    StaMap.put("applying_num",map1.get("num"));
                }else if(map1.get("status").equals(2)){
                    StaMap.put("enterprise_pass_num",map1.get("num"));
                }else if (map1.get("status").equals(4)){
                    StaMap.put("stu_pass_num",map1.get("num"));
                }
                int total = stuApplyMapper.countAllSta(expId);
                StaMap.put("total",total);
            }
            list.add(StaMap);
        }
        return list;
    }

    //企业注册
    public void register(Enterprise enterprise){
        enterpriseMapper.insertSelective(enterprise);
    }

    //企业登录
    public Enterprise login(String email,String password){
        Enterprise enterprise = enterpriseMapper.selectByEmail(email,password);
        return enterprise;
    }
}
