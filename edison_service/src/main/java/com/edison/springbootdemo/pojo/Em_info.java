package com.edison.springbootdemo.pojo;

import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * Em_info class
 *
 * @author fzj
 * @date 2019/6/9
 */

public class Em_info {
    private Integer em_id;
    private String em_name;
    private String em_sex;
    private Date em_birthday;
    private Date em_hiredate;
    private Double em_salary;

    public Integer getEm_id() {
        return em_id;
    }

    public void setEm_id(Integer em_id) {
        this.em_id = em_id;
    }

    public String getEm_name() {
        return em_name;
    }

    public void setEm_name(String em_name) {
        this.em_name = em_name;
    }

    public String getEm_sex() {
        return em_sex;
    }

    public void setEm_sex(String em_sex) {
        this.em_sex = em_sex;
    }

    public Date getEm_birthday() {
        return em_birthday;
    }

    public void setEm_birthday(Date em_birthday) {
        this.em_birthday = em_birthday;
    }

    public Date getEm_hiredate() {
        return em_hiredate;
    }

    public void setEm_hiredate(Date em_hiredate) {
        this.em_hiredate = em_hiredate;
    }

    public Double getEm_salary() {
        return em_salary;
    }

    public void setEm_salary(Double em_salary) {
        this.em_salary = em_salary;
    }

    @Override
    public String toString() {
        return "Em_info{" +
                "em_id=" + em_id +
                ", em_name='" + em_name + '\'' +
                ", em_sex='" + em_sex + '\'' +
                ", em_birthday=" + em_birthday +
                ", em_hiredate=" + em_hiredate +
                ", em_salary=" + em_salary +
                '}';
    }
}