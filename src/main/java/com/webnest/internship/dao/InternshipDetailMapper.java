package com.webnest.internship.dao;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.bean.InternshipDetailExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface InternshipDetailMapper {
    long countByExample(InternshipDetailExample example);

    int deleteByExample(InternshipDetailExample example);

    int deleteByPrimaryKey(Integer internshipId);

    int insert(InternshipDetail record);

    int insertSelective(InternshipDetail record);

    List<InternshipDetail> selectByExample(InternshipDetailExample example);

    List<InternshipDetail> selectByEntId(int EnterpriseId);

    List<Map<String,Object>> selectExpId(int enterpriseId);

    List<InternshipDetail> selectByEntIdAndSta(int EnterpriseId, String status);

    InternshipDetail selectByPrimaryKey(Integer internshipId);

    int updateByExampleSelective(@Param("record") InternshipDetail record, @Param("example") InternshipDetailExample example);

    int updateByExample(@Param("record") InternshipDetail record, @Param("example") InternshipDetailExample example);

    int updateByPrimaryKeySelective(InternshipDetail record);

    int updateByPrimaryKey(InternshipDetail record);
}