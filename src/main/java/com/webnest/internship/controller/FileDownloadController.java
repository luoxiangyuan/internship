package com.webnest.internship.controller;

import com.webnest.internship.bean.Msg;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class FileDownloadController {

    /**
     * @author: Luo
     * @description: 下载文件控制器
     * @date: 2019/11/20 18:43
     * @param URL 文件tomcat的映射地址
     * @return com.webnest.internship.bean.Msg
     */
    @GetMapping("/download")
    public Msg downFile(HttpServletRequest request, HttpServletResponse response, @RequestParam String URL){
    String rul = "http://39.108.172.6:8000/WebNest/uploadFiles/enterprise/null/实验十.zip";
    String path = "/usr/local/Springboot/WebNest/uploadFiles";
    return Msg.fail(response);
    }
}
