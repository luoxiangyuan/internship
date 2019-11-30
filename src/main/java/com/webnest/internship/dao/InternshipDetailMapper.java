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

    List<Map<String, Object>> selectAll();

    Map<String, Object> selectByExpId(int expId);

    List<Map<String, Object>> selectAllBySta(int status);

    List<InternshipDetail> selectByExample(InternshipDetailExample example);

    int getCheckNum(int expId);

    int getSubmitNum(int expId);

    int getEntCheckNum(int expId);

    List<InternshipDetail> selectByEntId(int EnterpriseId);

    List<Map<String, Object>> selectExpId(int enterpriseId);

    List<InternshipDetail> selectByEntIdAndSta(int EnterpriseId, int status);

    InternshipDetail selectByPrimaryKey(Integer internshipId);

    int updateByExampleSelective(@Param("record") InternshipDetail record, @Param("example") InternshipDetailExample example);

    int updateByExample(@Param("record") InternshipDetail record, @Param("example") InternshipDetailExample example);

    int updateByPrimaryKeySelective(InternshipDetail record);

    int updateByPrimaryKey(InternshipDetail record);
}