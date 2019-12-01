package com.webnest.internship.controller;

import com.github.pagehelper.PageHelper;
import com.webnest.internship.bean.Admin;
import com.webnest.internship.bean.Enterprise;
import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.bean.Msg;
import com.webnest.internship.service.AdminService;
import com.webnest.internship.service.EnterpriseService;
import com.webnest.internship.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    InternshipService internshipService;
    @Autowired
    AdminService adminService;
    @Autowired
    Msg msg;

    @PostMapping("/login")
    public Msg login(@RequestParam String email, @RequestParam String password,
                     HttpServletResponse response, HttpSession session) {
        Admin admin = adminService.login(email, password);
        if (admin != null) {
            session.setAttribute("adminId", admin.getAdminId());
            return Msg.success(response);
        }
        msg.setMsg("账号或密码错误，请重新输入");
        msg.setCode(100);
        response.setStatus(400);
        return msg;
    }

    //审核企业
    @PutMapping("/enterprise")
    public Msg checkEnt(@RequestParam String enterpriseId, @RequestParam int status, HttpServletResponse response) {
        Enterprise enterprise = enterpriseService.getEnt(Integer.valueOf(enterpriseId));
        enterprise.setStatus(status);
        enterpriseService.updateEnt(enterprise);
        return Msg.success(response);
    }

    //查询实训信息
    @GetMapping("/internship")
    public Msg getInternship(@RequestParam String expId, HttpServletResponse response) {
        Map<String, Object> exp = internshipService.getExp(Integer.valueOf(expId));
        return Msg.success(response).add(exp);
    }

    //审核实训
    @PutMapping("/internship")
    public Msg checkInternship(@RequestParam String expid, @RequestParam Integer status, HttpServletResponse response) {
        InternshipDetail internshipDetail = internshipService.getInternship(Integer.valueOf(expid));
        internshipDetail.setStatus(status);
        internshipService.updateInternship(internshipDetail);
        return Msg.success(response);
    }

    //分页查询实训
    @GetMapping("/internshiplist")
    public Msg getIntList(@RequestParam(required = false) Integer status, HttpServletResponse response) {
        if (status == null) {
            List<Map<String, Object>> allExp = internshipService.getAllExp();
            return Msg.success(response).add(allExp);
        } else {
            List<Map<String, Object>> allExpBySta = internshipService.getAllExpBySta(status);
            return Msg.success(response).add(allExpBySta);
        }
    }

    //分页查询企业
    @GetMapping("/enterpriselist")
    public Msg getEntList(HttpServletRequest request, @RequestParam(required = false) Integer status, HttpServletResponse response) {
        if (status == null) {
            List<Enterprise> all = enterpriseService.getAll();
            return Msg.success(response).add(all);
        } else {
            return Msg.success(response).add(enterpriseService.getAllBySta(status));
        }
    }

    //分年级管理学生
    @GetMapping("/stumanagelist")
    public Msg stuManage(@RequestParam String level, HttpServletResponse response) {
        List<Map<String, Object>> list = adminService.stuManage(level);
        return Msg.success(response).add(list);
    }
}
