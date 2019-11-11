package com.webnest.internship.dao;

import com.webnest.internship.bean.StuApply;
import com.webnest.internship.bean.StuApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StuApplyMapper {
    long countByExample(StuApplyExample example);

    int deleteByExample(StuApplyExample example);

    int deleteByPrimaryKey(Integer applyId);

    int insert(StuApply record);

    int insertSelective(StuApply record);

    List<StuApply> selectByExample(StuApplyExample example);

    StuApply selectByPrimaryKey(Integer applyId);

    int updateByExampleSelective(@Param("record") StuApply record, @Param("example") StuApplyExample example);

    int updateByExample(@Param("record") StuApply record, @Param("example") StuApplyExample example);

    int updateByPrimaryKeySelective(StuApply record);

    int updateByPrimaryKey(StuApply record);
}