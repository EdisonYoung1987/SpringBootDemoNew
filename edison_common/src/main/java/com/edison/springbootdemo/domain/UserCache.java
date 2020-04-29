package com.edison.springbootdemo.domain;

public class UserCache {
    /**用户id*/
    String userId;
    /**用户名称*/
    String name;
    /**用户手机号*/
    String telphone;
    /**用户性别 0-男 1-女*/
    int  sex;
    /**年龄*/
    int age;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
