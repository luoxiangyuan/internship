package com.webnest.internship.dao;

import com.webnest.internship.bean.Enterprise;
import com.webnest.internship.bean.EnterpriseExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface EnterpriseMapper {
    long countByExample(EnterpriseExample example);

    int deleteByExample(EnterpriseExample example);

    int deleteByPrimaryKey(Integer enterpriseId);

    int insert(Enterprise record);

    int insertSelective(Enterprise record);

    List<Enterprise> selectByExample(EnterpriseExample example);

    List<Enterprise> selectAllEnt();

    Enterprise selectByEmail(String email, String password);

    List<Enterprise> selectAllEntBysta(int status);

    Enterprise selectByPrimaryKey(Integer enterpriseId);

    int updateByExampleSelective(@Param("record") Enterprise record, @Param("example") EnterpriseExample example);

    int updateByExample(@Param("record") Enterprise record, @Param("example") EnterpriseExample example);

    int updateByPrimaryKeySelective(Enterprise record);

    int updateByPrimaryKey(Enterprise record);
}