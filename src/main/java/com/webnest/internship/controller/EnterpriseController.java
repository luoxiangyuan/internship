package com.webnest.internship.controller;

import com.webnest.internship.bean.*;
import com.webnest.internship.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseController {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    InternshipService internshipService;
    @Autowired
    ApplyService applyService;
    @Autowired
    AchievementService achievementService;
    @Autowired
    UploadFileService uploadFileService;
    @Autowired
    Msg msg;

    /**
     * @param type             类型：导师OR企业
     * @param name             名称
     * @param address          地址
     * @param email            邮箱
     * @param contact_name     联系人姓名
     * @param contact_tel      联系人电话
     * @param intro            简介
     * @param qualificate_file 担保书
     * @param images           环境图片
     * @param password         密码
     * @return com.webnest.internship.bean.Msg
     * @author: Luo
     * @description: 企业注册
     * @date: 2019/11/19 19:18
     */
    @PostMapping("/register")
    public Msg register(@RequestParam String type, @RequestParam String name, @RequestParam String address,
                        @RequestParam String email, @RequestParam String contact_name, @RequestParam String contact_tel,
                        @RequestParam String intro, @RequestParam String password,
                        @RequestParam(value = "qualificate_file") MultipartFile qualificate_file,
                        @RequestParam(value = "images") MultipartFile[] images,
                        HttpServletResponse response, HttpServletRequest request) {
        if (qualificate_file.isEmpty() || images.length == 0) {
            msg.setMsg("请上传担保书和照片");
            msg.setCode(100);
            response.setStatus(400);
            return msg;
        }
        String checkFile = uploadFileService.upload(qualificate_file, "/enterprise/" + name, request);
        String imageURL = "";
        for (MultipartFile image : images) {
            imageURL += uploadFileService.upload(image, "/enterprise/" + name + "/image", request)+",";
        }
        imageURL = imageURL.substring(0,imageURL.length()-1);
        Enterprise enterprise = new Enterprise();

        enterprise.setType(type);
        enterprise.setName(name);
        enterprise.setAddress(address);
        enterprise.setEmail(email);
        enterprise.setContactName(contact_name);
        enterprise.setContactTel(contact_tel);
        enterprise.setIntro(intro);
        enterprise.setPassword(password);
        enterprise.setCheckFile(checkFile);
        enterprise.setImages(imageURL.toString());
        enterprise.setRegisterTime(new Date());
        enterprise.setStatus(0);
        enterpriseService.register(enterprise);
        return Msg.success(response);
    }


    /**
     * @param email    登录邮箱
     * @param password 登录密码
     * @return com.webnest.internship.bean.Msg
     * @author: Luo
     * @description: 企业登录
     * @date: 2019/11/19 19:30
     */
    @PostMapping("/login")
    public Msg login(@RequestParam String email, @RequestParam String password,
                     HttpServletResponse response, HttpSession session) {
        Enterprise enterprise = enterpriseService.login(email, password);
        if (enterprise != null) {
            session.setAttribute("enterpriseId", enterprise.getEnterpriseId());
            System.out.println(session.getAttribute("enterpriseId").toString());
            return Msg.success(response);
        }
        msg.setMsg("账号或者密码错误，请重新输入");
        msg.setCode(100);
        response.setStatus(400);
        return msg;
    }


    /**
     * @param topic          实训话题
     * @param exp_begin_time 开始时间
     * @param exp_end_time   结束时间
     * @param description    描述
     * @param need_num       需要人数
     * @param apply_end_time 申请结束时间
     * @param session
     * @return com.webnest.internship.bean.Msg
     * @author: Luo
     * @description: 企业对实训的相关操作 post:发布/delete:删除/put:修改/get:查询
     * @date: 2019/11/13 9:39
     */
    @PostMapping("/internship")
    public Msg pubInternship(@RequestParam String topic, @RequestParam String exp_begin_time,
                             @RequestParam String exp_end_time, @RequestParam String description,
                             @RequestParam int need_num, @RequestParam String apply_end_time,
                             HttpSession session, HttpServletResponse response) {
        int EnterpriseId = (int) session.getAttribute("enterpriseId");  //获取session中的企业id
//        int EnterpriseId = 2;

        int status = (int) enterpriseService.getEntprise(EnterpriseId).get("status"); //获取企业当前审核状态
        if (status == 1) { //企业通过审核，可以发布实训
            InternshipDetail newInternship = new InternshipDetail();
            //封装internship类
            newInternship.setTopic(topic);
            newInternship.setExpBeginTime(new Date(new Long(exp_begin_time)));
            newInternship.setExpEndTime(new Date(new Long(exp_end_time)));
            newInternship.setDescription(description);
            newInternship.setNeedNum(need_num);
            newInternship.setApplyEndTime(new Date(new Long(apply_end_time)));
            newInternship.setEnterpriseId(EnterpriseId);
            newInternship.setStatus(0);
            newInternship.setSubmitTime(new Date());
            newInternship.setExpModifyTime(new Date());

            internshipService.createInternship(newInternship);
            return Msg.success(response);
        } else {
            msg.setMsg("企业未通过审核，暂不能发布实训");
            msg.setCode(100);
            response.setStatus(400);
        }
        return msg;
    }

    @DeleteMapping("/internship")
    public Msg delInternship(@RequestParam String expId, HttpServletResponse response, HttpSession session) {
        InternshipDetail internship = internshipService.getInternship(Integer.valueOf(expId));
        if (internship == null) {
            msg.setMsg("没有此实训");
            msg.setCode(100);
            return msg;
        }
        if (internship.getEnterpriseId() != (int) session.getAttribute("enterpriseId")) {
            msg.setMsg("无法删除不是您公司发布的实训");
            msg.setCode(100);
            return msg;
        } else if (internship.getStatus() == 1) {
            msg.setMsg("无法删除已通过审核的实训");
            msg.setCode(100);
            return msg;
        }
        internshipService.delInternship(Integer.valueOf(expId));
        return Msg.success(response);
    }

    @PutMapping("/internship")
    public Msg updateInternship(@RequestParam String expId, @RequestParam String topic, @RequestParam String exp_begin_time,
                                @RequestParam String exp_end_time, @RequestParam String description,
                                @RequestParam int need_num, @RequestParam String apply_end_time, HttpServletResponse response,
                                HttpSession session) {
        InternshipDetail internship = internshipService.getInternship(Integer.valueOf(expId));
        if (internship == null) {
            msg.setMsg("没有此实训");
            msg.setCode(100);
            return msg;
        } else if (internship.getEnterpriseId() != (int) session.getAttribute("enterpriseId")) {
            msg.setMsg("无法删除不是您公司发布的实训");
            msg.setCode(100);
        } else if (internship.getStatus() == 1) {
            msg.setMsg("该实训已通过审核，不可修改信息");
            msg.setCode(100);
            response.setStatus(400);
        } else {
            InternshipDetail updateInternship = internshipService.getInternship(Integer.valueOf(expId));
            //封装internship类
            updateInternship.setTopic(topic);
            updateInternship.setExpBeginTime(new Date(new Long(exp_begin_time)));
            updateInternship.setExpEndTime(new Date(new Long(exp_end_time)));
            updateInternship.setDescription(description);
            updateInternship.setNeedNum(need_num);
            updateInternship.setApplyEndTime(new Date(new Long(apply_end_time)));
            updateInternship.setExpModifyTime(new Date());

            internshipService.updateInternship(updateInternship);
            return Msg.success(response);
        }
        return msg;
    }

    @GetMapping("/internship")
    public Msg getInternship(@RequestParam Integer expId, HttpServletResponse response, HttpSession session) {
        InternshipDetail internship = internshipService.getInternship(Integer.valueOf(expId));
        if (internship == null) {
            msg.setMsg("没有此实训");
            msg.setCode(100);
            return msg;
        } else if (internship.getEnterpriseId() != (int) session.getAttribute("enterpriseId")) {
            msg.setMsg("无法查询不是您公司发布的实训");
            msg.setCode(100);
            return msg;
        }
        return Msg.success(response).add(internshipService.getExp(expId));
    }

    /*
     * 查询企业信息
     * 若为企业登录，取得session中的信息，获取登录的企业信息
     * 若为学校登录，取得参数，根据参数查询企业信息
     * */
    @GetMapping("/detail")
    public Msg getEnterprise(HttpSession session, HttpServletResponse response, @RequestParam(required = false) String enterprise) {
        if (enterprise == null) { //当前为学校
            Integer enterpriseId = (Integer) session.getAttribute("enterpriseId");
            return Msg.success(response).add(enterpriseService.getEntprise(enterpriseId));
        }
        if (enterprise != null) { //当前为企业
            return Msg.success(response).add(enterpriseService.getEntprise(Integer.valueOf(enterprise)));
        }
        return Msg.fail(response);
    }

    //处理申请
    @PutMapping("/application")
    public Msg putApp(@RequestParam String apply_id, @RequestParam int apply_status, HttpServletResponse response) {
        StuApply stuApply = applyService.getApply(Integer.valueOf(apply_id));
        stuApply.setApplyStatus(apply_status);
        applyService.putApp(stuApply);
        return Msg.success(response);
    }

    /**
     * @param internshipId 实训id
     * @param status       申请状态 --非必须，没有传此参数时，返回所有状态的申请，否则返回指定状态的申请
     * @return
     * @author: Luo
     * @description: 分页查询申请
     * @date: 2019/11/13 20:31
     */
    @GetMapping("/applicationlist")
    public Msg getApplyList(@RequestParam String internshipId, @RequestParam(required = false) Integer status
            , HttpServletResponse response, HttpSession session) {
        InternshipDetail internship = internshipService.getInternship(Integer.valueOf(internshipId));
        if (internship.getEnterpriseId() != (int) session.getAttribute("enterpriseId")) {
            msg.setMsg("无法获得不是您公司发布的实训");
            msg.setCode(100);
            return msg;
        }
        if (status == null) {
            return Msg.success(response).add(applyService.getApplyList(Integer.valueOf(internshipId)));
        } else {
            return Msg.success(response).add(applyService.getApplyListBySta(Integer.valueOf(internshipId), status));
        }
    }

    //分页查询实训
    @GetMapping("/internshiplist")
    public Msg getExpList(@RequestParam(required = false) Integer status, HttpSession session
            , HttpServletResponse response) {
        int EnterpriseId = (int) session.getAttribute("enterpriseId");
//        int EnterpriseId = 37;
        List<Map<String, Object>> list = new ArrayList<>();
        if (status == null) { //没有传入status参数，返回公司的所有实训信息
            list = internshipService.getList(EnterpriseId);
        } else {
            list = internshipService.getListBySta(EnterpriseId, status);
        }
        return Msg.success(response).add(list);
    }

    //上传实训成绩
    @PostMapping("/result/mark")
    public Msg putMark(@RequestParam String apply_id, @RequestParam String mark, HttpServletResponse response) {
        StuAchievement stuAchievement = achievementService.getAchievement(Integer.valueOf(apply_id));
        stuAchievement.setMark(Double.valueOf(mark));
        achievementService.updateAchievement(stuAchievement);

        if (achievementService.checkAchievement(Integer.valueOf(apply_id))) { //判断成绩表中的所有列是否都满，如果都满，将申请表的状态改为结业
            StuApply stuApply = applyService.getApply(Integer.valueOf(apply_id));
            stuApply.setApplyStatus(6);
            applyService.putApp(stuApply);
        }
        return Msg.success(response);
    }


    //学生管理
    @GetMapping("/stumanagelist")
    public Msg stuManege(@RequestParam String exp_id, HttpServletResponse response, HttpSession session) {
        InternshipDetail internship = internshipService.getInternship(Integer.valueOf(exp_id));
        if (internship.getEnterpriseId() != (int) session.getAttribute("enterpriseId")) {
            msg.setMsg("无法查询不是您公司发布的实训");
            msg.setCode(100);
            return msg;
        }
        return Msg.success(response).add(applyService.ManageStu(Integer.valueOf(exp_id)));
    }

    //查询申请数目
    @GetMapping("/application/count")
    public Msg countApply(HttpSession session, HttpServletResponse response) {
        int EnterpriseId = (int) session.getAttribute("enterpriseId");
//        int EnterpriseId = 37;
        List<Map<String, Object>> list1 = enterpriseService.countSta(EnterpriseId);
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> map : list1) {
            if (!map.isEmpty()) {
                list.add(map);
            }
        }
        return Msg.success(response).add(list);
    }

    //企业上传结业证明
    @PostMapping("/result/certificate")
    public Msg uploadCertificate(HttpServletRequest req, HttpServletResponse response, @RequestParam(value = "certificate") MultipartFile[] multipartFiles) {
        if (multipartFiles.length == 0) {
            msg.setMsg("请上传结业证明");
            msg.setCode(100);
            return msg;
        }
        int expId = Integer.valueOf(req.getParameter("exp_id"));
        String filePath = new String();
        for (int i = 0; i < multipartFiles.length; i++) {
            String path = "/achievement/" + expId;
            filePath = uploadFileService.upload(multipartFiles[i], path, req);
        }

        List<StuApply> applies = applyService.getApplyList1(expId);
        for (StuApply apply : applies) {
            int applyId = apply.getApplyId();
            StuAchievement achievement = achievementService.getAchievement(applyId);
            achievement.setCertificate(filePath);
            achievementService.updateAchievement(achievement);
        }
        return Msg.success(response);
    }
}
