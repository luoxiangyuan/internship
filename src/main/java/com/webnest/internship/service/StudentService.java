package com.webnest.internship.service;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.bean.StuApplyExample;
import com.webnest.internship.bean.Student;
import com.webnest.internship.dao.InternshipDetailMapper;
import com.webnest.internship.dao.StuApplyMapper;
import com.webnest.internship.dao.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import com.webnest.internship.bean.StuApply;
/**
 * @auther: Monster
 * @date: 2019/11/14
 * @description:
 */

@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    InternshipDetailMapper internshipDetailMapper;

    @Autowired
    StuApplyMapper stuApplyMapper;

    /**
     * 学生登陆
     * @param email 邮箱
     * @param password 密码
     * @return 登陆成功 or 失败
     */
    public boolean login(String email, String password){
        List<Student> list = studentMapper.selectByExample(null);
        for(Student student:list){
            if(student.getEmail().equals(email)){
                if(student.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 学生注册
     * @param student 学生实体类字段
     * @return 注册成功 or 失败
     */
    public boolean register(Student student){
        List<Student> list = studentMapper.selectByExample(null);
        for(Student student1:list){
            if(student1.getEmail().equals(student.getEmail())){
                return false;
            }
        }
        studentMapper.insert(student);
        return true;
    }

    /**
     * 查询某个实训信息
     * @param id 实训id
     * @return InternshipDetails 实体类 具体信息
     */
    public InternshipDetail getInternship(String id){
        return internshipDetailMapper.selectByPrimaryKey(Integer.valueOf(id));
    }

    /**
     * 查询所有实训信息
     * @return List<InternshipDetail> 封装 所有实训信息
     */
    public List<InternshipDetail> getInternshipList(){
        return internshipDetailMapper.selectByExample(null);
    }

    /**
     * 学生报名实训
     * @param id 实训id
     * @param studentId 学生id
     */
    public void postApplication(int id, String studentId){
        stuApplyMapper.insertSelective(new StuApply(null, studentId, id, "0", new Date()));
    }

    /**
     * 学生确认实训
     * @param applyId 报名id
     * @param id 实训id
     * @param studentId 学生id
     */
    public void putApplication(int applyId, int id, String studentId){
        stuApplyMapper.updateByPrimaryKeySelective(new StuApply(applyId, studentId, id , "4",null));
    }

    /**
     * 根据学生id，获取学生信息
     * @param id 学生id
     * @return 学生详细信息
     */
    public Student getStudent(String id){
        return studentMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取学生申请
     * @param studentId 学生id
     * @return 学生所有申请信息
     */
    public List<StuApply> getApplicationList(String studentId){
        List<StuApply> applicationList;
        List<StuApply> stuApply = stuApplyMapper.selectByExample(null);
        for(StuApply stuapply : stuApply){
            if (stuapply.getStuId().equals(studentId)){
                applicationList.add(stuapply);
            }
        }
        return applicationList;
    }
}