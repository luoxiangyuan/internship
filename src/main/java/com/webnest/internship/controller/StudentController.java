package com.webnest.internship.controller;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.bean.Msg;
import com.webnest.internship.bean.StuApply;
import com.webnest.internship.bean.Student;
import com.webnest.internship.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param email    邮箱
     * @param password 密码
     * @return Msg类封装，登陆成功 or 失败
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Msg login(HttpServletRequest request,
                     HttpServletResponse response,
                     @RequestParam(value = "email") String email,
                     @RequestParam(value = "password") String password,
                     HttpSession session) {
        System.out.println("controller email"+email);
        if (studentService.login(email, password)) {

            String StuID=studentService.getStudentIDByEmail(email);
            System.out.println(StuID);
            session.setAttribute("studentId", StuID);
            return Msg.success(response);
        }
        return Msg.fail(response);
    }

    /**
     * 学生注册
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param student  学生实体类的属性
     * @return Msg类封装，注册成功 or 失败
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Msg Register(HttpServletRequest request,
                        HttpServletResponse response,
                        Student student) {
        if (studentService.register(student)) {
            return Msg.success(response);
        }
        return Msg.fail(response);
    }

    /**
     * 学生查询某个实训信息
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param id       实训id
     * @return Msg类封装 查询到的某个实训信息
     */
    @ResponseBody
    @RequestMapping(value = "/internship", method = RequestMethod.GET)
    public Msg getInternship(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(value = "expId") String id) {
        InternshipDetail internshipDetail = studentService.getInternship(id);
        return Msg.success(response).add(internshipDetail);
    }

    /**
     * 学生查询所有实训信息
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Msg类封装 查询到的所有实训信息
     */
    @ResponseBody
    @RequestMapping(value = "/internshiplist", method = RequestMethod.GET)
    public Msg getInternshipList(HttpServletRequest request,
                                 HttpServletResponse response) {
        List<InternshipDetail> list = studentService.getInternshipList();
        return Msg.success(response).add(list);
    }

    /**
     * 学生报名实训
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param id       实训id
     * @param session  HttpSession
     * @return Msg类封装 报名成功 or 失败
     */
    @ResponseBody
    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public Msg postApplication(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "exp_id") String id,
                               HttpSession session) {
        String studentId = session.getAttribute("studentId").toString();
        studentService.postApplication(Integer.valueOf(id), studentId);
        return Msg.success(response);
    }

//    /**
//     * 学生确认实训
//     *
//     * @param request  HttpServletRequest
//     * @param response HttpServletResponse
//     * @param id       实训id
//     * @param applyId  报名id
//     * @param session  HttpSession
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/application", method = RequestMethod.PUT)
//    public Msg PutApplication(HttpServletRequest request,
//                              HttpServletResponse response,
//                              @RequestParam(value = "exp_id") String id,
//                              @RequestParam(value = "apply_id") int applyId,
//                              HttpSession session) {
//        String studentId = session.getAttribute("studentId").toString();
//        studentService.putApplication(applyId, Integer.valueOf(id), studentId);
//        return Msg.success(response);
//    }

    /**
     * 查询学生信息
     *
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param studentId 学生id
     * @return Msg类封装 查询到的某个学生信息
     */
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Msg getStudent(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(value = "student") String studentId) {
        return Msg.success(response).add(studentService.getStudent(studentId));
    }

    /**
     * 分页查询个人申请
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param session  HttpSession
     * @return Msg类封装 查询到的个人申请信息
     */
    @ResponseBody
    @RequestMapping(value = "/applicationlist", method = RequestMethod.GET)
    public Msg applicationList(HttpServletRequest request,
                               HttpServletResponse response,
                               HttpSession session) {
        String studentId = session.getAttribute("studentId").toString();
//        String studentId = "1725121013";
        return Msg.success(response).add(studentService.getApplicationList(studentId));
    }

    /**
     * 学生修改个人信息
     *
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param session      HttpSession
     * @param major        专业
     * @param tel          电话
     * @param introduction 简介
     * @param exps         简介
     * @return Msg类封装 修改个人信息成功
     */
    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Msg edit(HttpServletRequest request,
                    HttpServletResponse response,
                    HttpSession session,
                    @RequestParam(value = "major") String major,
                    @RequestParam(value = "tel") String tel,
                    @RequestParam(value = "introduction") String introduction,
                    @RequestParam(value = "exps") String exps) {
        String studentId = session.getAttribute("studentId").toString();
        studentService.edit(studentId, major, tel, introduction, exps);
        return Msg.success(response);
    }

    /**
     * 查询个人信息
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param session  HttpSession
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/myinfo", method = RequestMethod.GET)
    public Msg myInfo(HttpServletRequest request,
                      HttpServletResponse response,
                      HttpSession session) {
        String studentId = session.getAttribute("studentId").toString();
        Student student = studentService.myInfo(studentId);
        return Msg.success(response).add(student);
    }

    /**
     * 新  学生确认
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param session      HttpSession
     * @param apply_id     申请ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/confirm", method = RequestMethod.PUT)
    public Msg confirm(HttpServletRequest request,
                       HttpServletResponse response,
                       HttpSession session,
                       @RequestParam(value = "apply_id") String apply_id) {
//        1、确认是当前学生的applyid
        boolean permission = false;
        String studentId = session.getAttribute("studentId").toString();
//        String studentId = "1725121013";
        List<StuApply> lists = studentService.getApplicationList(studentId);
        for (StuApply list : lists) {
            if (list.getStuId().equals(studentId)) {
                permission = true;
            }
        }
//        2、删除更改apply_id的状态
//        3、更改该学生其他申请的状态
        if (permission) {
            for(StuApply list : lists){
                studentService.putApplication(Integer.valueOf(list.getApplyId()),5);
            }
            studentService.putApplication(Integer.valueOf(apply_id),4);
            return Msg.success(response);
        }
        return Msg.fail(response);
    }
}
