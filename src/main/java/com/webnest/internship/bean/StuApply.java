package com.webnest.internship.bean;


import java.util.Date;

public class StuApply {
    public StuApply(Integer applyId, String stuId, Integer expId, int applyStatus, Date submitTime) {
        this.applyId = applyId;
        this.stuId = stuId;
        this.expId = expId;
        this.applyStatus = applyStatus;
        this.submitTime = submitTime;
    }

    private Integer applyId;

    private String stuId;

    private Integer expId;

    private int applyStatus;

    private Date submitTime;

    private Student student;

    private String topic;

    private String enterprise;

    private Date exp_begin_time;

    private Date exp_end_time;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public Date getExp_begin_time() {
        return exp_begin_time;
    }

    public void setExp_begin_time(Date exp_begin_time) {
        this.exp_begin_time = exp_begin_time;
    }

    public Date getExp_end_time() {
        return exp_end_time;
    }

    public void setExp_end_time(Date exp_end_time) {
        this.exp_end_time = exp_end_time;
    }

    public StuApply() {

    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId == null ? null : stuId.trim();
    }

    public Integer getExpId() {
        return expId;
    }

    public void setExpId(Integer expId) {
        this.expId = expId;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}