package com.webnest.internship.dao;

import com.webnest.internship.bean.Student;
import com.webnest.internship.bean.StudentExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface StudentMapper {
    long countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(String stuId);

    int insert(Student record);

    int insertSelective(Student record);

    List<Map<String,Object>> selectApplyIdByGrade(String grade);

    List<Map<String,Object>> selectByGrade(String grade);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(String stuId);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}