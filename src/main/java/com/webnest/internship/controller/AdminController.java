package com.webnest.internship.controller;

import com.github.pagehelper.PageHelper;
import com.webnest.internship.bean.Enterprise;
import com.webnest.internship.bean.InternshipDetail;
import com.webnest.internship.bean.Msg;
import com.webnest.internship.service.EnterpriseService;
import com.webnest.internship.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    InternshipService internshipService;

    //审核企业
    @PutMapping("/enterprise")
    public Msg checkEnt(@RequestParam String enterpriseId, @RequestParam String status, HttpServletResponse response) {
        Enterprise enterprise = new Enterprise();
        enterprise.setEnterpriseId(Integer.valueOf(enterpriseId));
        enterprise.setStatus(status);
        enterpriseService.updateEnt(enterprise);
        return Msg.success(response);
    }

    //查询实训信息
    @GetMapping("/internship")
    public Msg getInternship(@RequestParam String expId, HttpServletResponse response) {
        InternshipDetail internship = internshipService.getInternship(Integer.valueOf(expId));
        return Msg.success(response).add(internship);
    }

    //审核实训
    @PostMapping("/internship")
    public Msg checkInternship(@RequestParam String expid, @RequestParam boolean passed, HttpServletResponse response) {
        InternshipDetail internshipDetail = new InternshipDetail();
        internshipDetail.setInternshipId(Integer.valueOf(expid));
        if (passed == true) {
            internshipDetail.setStatus("1");
        } else {
            internshipDetail.setStatus("2");
        }
        internshipService.updateInternship(internshipDetail);
        return Msg.success(response);
    }

    //分页查询实训
    @GetMapping("/internshiplist")
    public Msg getIntList(@RequestParam(required = false) String status, HttpServletResponse response) {
        if (status == null){

        }
        return Msg.fail(response);
    }

    //分页查询企业
    @GetMapping("/enterpriselist")
    public Msg getEntList(@RequestParam int page, @RequestParam(required = false) Integer status,HttpServletResponse response){
        PageHelper.startPage(page,10);

        if (status == null){
            List<Enterprise> all = enterpriseService.getAll();
            return Msg.success(response).add(all);
        }else {
            return Msg.success(response).add(enterpriseService.getAllBySta(status));
        }
    }


}
