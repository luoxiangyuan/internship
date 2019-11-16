package com.webnest.internship.controller;

import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.bean.Msg;
import com.webnest.internship.bean.StuAchievement;
import com.webnest.internship.bean.StuApply;
import com.webnest.internship.service.AchievementService;
import com.webnest.internship.service.ApplyService;
import com.webnest.internship.service.EnterpriseService;
import com.webnest.internship.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;


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


    /**
     * @author: Luo
     * @description: 企业对实训的相关操作 post:发布/delete:删除/put:修改/get:查询
     * @date: 2019/11/13 9:39
     * @param topic 实训话题
     * @param exp_begin_time 开始时间
     * @param exp_end_time 结束时间
     * @param description 描述
     * @param need_num 需要人数
     * @param apply_end_time 申请结束时间
     * @param session
     * @return com.webnest.internship.bean.Msg
     */
    @PostMapping("/internship")
    public Msg pubInternship(@RequestParam String topic, @RequestParam Date exp_begin_time,
                             @RequestParam Date exp_end_time, @RequestParam String description,
                             @RequestParam int need_num, @RequestParam Date apply_end_time,
                             HttpSession session, HttpServletResponse response){
        int EnterpriseId = (int) session.getAttribute("enterpriseId");  //获取session中的企业id
        String status = enterpriseService.getEntprise(EnterpriseId).getStatus(); //获取企业当前审核状态
        if (status.equals('1')){ //企业通过审核，可以发布实训
            InternshipDetail newInternship = new InternshipDetail();
            //封装internship类
            newInternship.setTopic(topic);
            newInternship.setExpBeginTime(exp_begin_time);
            newInternship.setExpEndTime(exp_end_time);
            newInternship.setDescription(description);
            newInternship.setNeedNum(need_num);
            newInternship.setApplyEndTime(apply_end_time);
            newInternship.setEnterpriseId(EnterpriseId);
            newInternship.setStatus("0");
            newInternship.setSubmitTime(new Date());
            newInternship.setExpModifyTime(new Date());

            internshipService.createInternship(newInternship);
            return Msg.success(response);
        }else {
            Msg.fail(response).setMsg("企业未通过审核，暂不能发布实训");
        }
        return Msg.fail(response);
    }

    @DeleteMapping("/internship")
    public Msg delInternship(@RequestParam String expId, HttpServletResponse response){
        internshipService.delInternship(Integer.valueOf(expId));
        return Msg.success(response);
    }

    @PutMapping("/internship")
    public Msg updateInternship(@RequestParam String expId,@RequestParam String topic, @RequestParam Date exp_begin_time,
                                @RequestParam Date exp_end_time, @RequestParam String description,
                                @RequestParam int need_num, @RequestParam Date apply_end_time, HttpServletResponse response){
        String internshipSta = internshipService.getInternship(Integer.valueOf(expId)).getStatus();
        if (internshipSta.equals("2")){
            Msg.fail(response).setMsg("该实训已通过审核，不可修改信息");
        }else {
            InternshipDetail updateInternship = new InternshipDetail();
            //封装internship类
            updateInternship.setTopic(topic);
            updateInternship.setExpBeginTime(exp_begin_time);
            updateInternship.setExpEndTime(exp_end_time);
            updateInternship.setDescription(description);
            updateInternship.setNeedNum(need_num);
            updateInternship.setApplyEndTime(apply_end_time);
            updateInternship.setExpModifyTime(new Date());

            internshipService.updateInternship(updateInternship);
            return Msg.success(response);
        }
        return Msg.fail(response);
    }

    @GetMapping("/internship")
    public Msg getInternship(@RequestParam Integer expId, HttpServletResponse response){
        return Msg.success(response).add(internshipService.getInternship(expId));
    }

    //查询企业信息
    @GetMapping("/detail")
    public Msg getEnterprise(HttpSession session, HttpServletResponse response){
        int enterpriseId = (int) session.getAttribute("enterpriseId");
        return Msg.success(response).add(enterpriseService.getEntprise(enterpriseId));
    }

    //处理申请
    @PutMapping("/application")
    public Msg putApp(@RequestParam int apply_id, @RequestParam String apply_status, HttpServletResponse response){
        StuApply stuApply = new StuApply();
        stuApply.setApplyId(apply_id);
        stuApply.setApplyStatus(apply_status);
        applyService.putApp(stuApply);
        return Msg.success(response);
    }

    /**
     * @author: Luo
     * @description: 分页查询申请
     * @date: 2019/11/13 20:31
     * @param internshipId 实训id
     * @param page 页数
     * @param status 申请状态 --非必须，没有传此参数时，返回所有状态的申请，否则返回指定状态的申请
     * @return
     */
    @GetMapping("/applicationlist")
    public Msg getApplyList(@RequestParam String internshipId, @RequestParam int page, @RequestParam(required = false) Integer status
                                , HttpServletResponse response){
        if (status == null){
            return Msg.success(response).add(applyService.getApplyList(Integer.valueOf(internshipId)));
        }else {
            return Msg.success(response).add(applyService.getApplyListBySta(Integer.valueOf(internshipId),String.valueOf(status)));
        }
    }

    //分页查询实训
    public Msg getExpList(@RequestParam int page, @RequestParam(required = false) String status, HttpSession session
                            , HttpServletResponse response){
        int EnterpriseId = (int) session.getAttribute("EnterpriseId");
        List<InternshipDetail> list;
        if (status == null){ //没有传入status参数，返回公司的所有实训信息
            list = internshipService.getList(EnterpriseId);
        }else {
            list = internshipService.getListBySta(EnterpriseId, status);
        }
        return Msg.success(response).add(list);
    }

    //上传实训成绩
    @PostMapping("/result/mark")
    public Msg putMark(@RequestParam String apply_id, @RequestParam String mark, HttpServletResponse response){
        StuAchievement stuAchievement = new StuAchievement();
        stuAchievement.setApplyId(Integer.valueOf(apply_id));
        stuAchievement.setMark(Double.valueOf(mark));
        achievementService.updateAchievement(stuAchievement);

        if (achievementService.checkAchievement(Integer.valueOf(apply_id))){ //判断成绩表中的所有列是否都满，如果都满，将申请表的状态改为结业
            StuApply stuApply = new StuApply();
            stuApply.setApplyId(Integer.valueOf(apply_id));
            stuApply.setApplyStatus("6");
            applyService.putApp(stuApply);
        }
        return Msg.success(response);
    }



    //学生管理
    public Msg stuManege(@RequestParam String exp_id, @RequestParam int page, HttpServletResponse response){
        return Msg.success(response).add(applyService.ManageStu(Integer.valueOf(exp_id)));
    }


}
