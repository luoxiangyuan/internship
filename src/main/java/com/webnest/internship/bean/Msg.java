package com.webnest.internship.bean;

import org.apache.ibatis.session.defaults.DefaultSqlSession;
import sun.awt.SunHints;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class Msg {
    // 状态码 100--成功 200 -- 失败
    private int code;
    // 提示信息
    private String msg;
    //返回给前端的数据
    private Map<String,Object> data = new HashMap<String, Object>();

    //处理成功
    public static Msg success(){
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("处理成功");
        return result;
    }

    //处理失败
    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("处理失败");
        return result;
    }

    //链式操作，向Msg的data添加JSON
    public Msg add(String key,Object value){
        this.getData().put(key,value);
        return this;
    }

    //Getter
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    //Setter
    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
