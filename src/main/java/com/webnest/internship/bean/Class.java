package com.webnest.internship.bean;

public class Class {
    private Integer classId;

    private String name;

    public int getEnterenceYear() {
        return enterenceYear;
    }

    public void setEnterenceYear(int enterenceYear) {
        this.enterenceYear = enterenceYear;
    }

    private int enterenceYear;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}