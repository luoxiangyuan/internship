package com.webnest.internship.bean;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;


@Component
public class Msg {
    // 状态码 200--成功 100 -- 失败
    private int code;
    // 提示信息
    private String msg;
    //返回给前端的数据
    private Object data;

    //处理成功
    public static Msg success(HttpServletResponse response) {
        Msg result = new Msg();
        response.setStatus(200);
        result.setCode(200);
        result.setMsg("处理成功");
        return result;
    }

    //处理失败
    public static Msg fail(HttpServletResponse response) {
        Msg result = new Msg();
        response.setStatus(400);
        result.setCode(100);
        result.setMsg("处理失败");
        return result;
    }

    //链式操作，向Msg的data添加JSON
    public Msg add(Object obj) {
        this.setData(obj);
        return this;
    }

    //Getter
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    //Setter
    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
