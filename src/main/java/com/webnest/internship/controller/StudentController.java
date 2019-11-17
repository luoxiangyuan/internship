package com.webnest.internship.controller;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.bean.Msg;
import com.webnest.internship.bean.Student;
import com.webnest.internship.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

/**
 * @auther: Monster
 * @date: 2019/11/14
 * @description:
 */

@Controller
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 学生登陆
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param email 邮箱
     * @param password 密码
     * @return Msg类封装，登陆成功 or 失败
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(HttpServletRequest request,
                     HttpServletResponse response,
                     @RequestParam(value = "email") String email,
                     @RequestParam(value = "password") String password,
                     HttpSession session){
        if(studentService.login(email, password)){
            session.setAttribute("studentId", email);
            return Msg.success(response);
        }
        return Msg.fail(response);
    }

    /**
     * 学生注册
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param student 学生实体类的属性
     * @return Msg类封装，注册成功 or 失败
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Msg Register(HttpServletRequest request,
                        HttpServletResponse response,
                        Student student){
        if(studentService.register(student)){
            return Msg.success(response);
        }
        return Msg.fail(response);
    }

    /**
     * 学生查询某个实训信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param id 实训id
     * @return Msg类封装 查询到的某个实训信息
     */
    @ResponseBody
    @RequestMapping(value = "/internship", method = RequestMethod.GET)
    public Msg getInternship(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(value = "expId")String id){
        InternshipDetail internshipDetail = studentService.getInternship(id);
        return Msg.success(response).add(internshipDetail);
    }

    /**
     * 学生查询所有实训信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg类封装 查询到的所有实训信息
     */
    @ResponseBody
    @RequestMapping(value = "/internshiplist", method = RequestMethod.GET)
    public Msg getInternshipList(HttpServletRequest request,
                                 HttpServletResponse response){
        List<InternshipDetail> list = studentService.getInternshipList();
        return Msg.success(response).add(list);
    }

    /**
     * 学生报名实训
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param id 实训id
     * @param session HttpSession
     * @return Msg类封装 报名成功 or 失败
     */
    @ResponseBody
    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public Msg postApplication(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(value = "exp_id") String id,
                           HttpSession session){
        String studentId = session.getAttribute("studentId").toString();
        studentService.postApplication(Integer.valueOf(id), studentId);
        return Msg.success(response);
    }

    /**
     * 学生确认实训
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param id 实训id
     * @param applyId 报名id
     * @param session HttpSession
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/application", method = RequestMethod.PUT)
    public Msg PutApplication(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam(value = "exp_id") String id,
                              @RequestParam(value = "apply_id") int applyId,
                              HttpSession session){
        String studentId = session.getAttribute("studentId").toString();
        studentService.putApplication(applyId, Integer.valueOf(id), studentId);
        return Msg.success(response);
    }

    /**
     * 查询学生信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param studentId 学生id
     * @return Msg类封装 查询到的某个学生信息
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Msg getStudent(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(value = "student") String studentId){
        return Msg.success(response).add(studentService.getStudent(studentId));
    }

    @ResponseBody
    @RequestMapping(value = "/applicationlist", method = RequestMethod.GET)
    public Msg applicationList(HttpServletRequest request,
                               HttpServletResponse response,
                               HttpSession session){
        String studentId = session.getAttribute("studentId").toString();
        return studentService.getApplicationList(studentId);
    }
}
