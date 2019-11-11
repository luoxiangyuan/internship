package com.webnest.internship.dao;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.bean.InternshipDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InternshipDetailMapper {
    long countByExample(InternshipDetailExample example);

    int deleteByExample(InternshipDetailExample example);

    int deleteByPrimaryKey(Integer internshipId);

    int insert(InternshipDetail record);

    int insertSelective(InternshipDetail record);

    List<InternshipDetail> selectByExample(InternshipDetailExample example);

    InternshipDetail selectByPrimaryKey(Integer internshipId);

    int updateByExampleSelective(@Param("record") InternshipDetail record, @Param("example") InternshipDetailExample example);

    int updateByExample(@Param("record") InternshipDetail record, @Param("example") InternshipDetailExample example);

    int updateByPrimaryKeySelective(InternshipDetail record);

    int updateByPrimaryKey(InternshipDetail record);
}