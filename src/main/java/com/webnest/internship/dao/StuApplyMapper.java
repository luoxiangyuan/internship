package com.webnest.internship.dao;

import com.webnest.internship.bean.StuApply;
import com.webnest.internship.bean.StuApplyExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface StuApplyMapper {
    long countByExample(StuApplyExample example);

    int deleteByExample(StuApplyExample example);

    int deleteByPrimaryKey(Integer applyId);

    int insert(StuApply record);

    int insertSelective(StuApply record);

    List<StuApply> selectByExample(StuApplyExample example);

    List<Map<String,Object>> selectByExpId(int exp_id);

    List<Map<String,Object>> selectByExpIdSta(int exp_id, String status);

    List<Map<String,Object>> selectStu(int exp_id);

    StuApply selectByPrimaryKey(Integer applyId);

    int updateByExampleSelective(@Param("record") StuApply record, @Param("example") StuApplyExample example);

    int updateByExample(@Param("record") StuApply record, @Param("example") StuApplyExample example);

    int updateByPrimaryKeySelective(StuApply record);

    int updateByPrimaryKey(StuApply record);
}