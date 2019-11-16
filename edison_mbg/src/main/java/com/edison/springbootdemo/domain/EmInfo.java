package com.edison.springbootdemo.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class EmInfo implements Serializable {
    /**
     * 员工ID
     */
    private Integer emId;

    /**
     * 员工姓名
     */
    private String emName;

    /**
     * 性别
     */
    private String emSex;

    /**
     * 出生日期
     */
    private Date emBirthday;

    /**
     * 入职日期
     */
    private Date emHiredate;

    /**
     * 工资
     */
    private Double emSalary;

    private static final long serialVersionUID = 1L;

    /**获取员工ID */
    public Integer getEmId() {
        return emId;
    }

    /**设置员工ID*/
    public void setEmId(Integer emId) {
        this.emId = emId;
    }

    /**获取员工姓名 */
    public String getEmName() {
        return emName;
    }

    /**设置员工姓名*/
    public void setEmName(String emName) {
        this.emName = emName;
    }

    /**获取性别 */
    public String getEmSex() {
        return emSex;
    }

    /**设置性别*/
    public void setEmSex(String emSex) {
        this.emSex = emSex;
    }

    /**获取出生日期 */
    public Date getEmBirthday() {
        return emBirthday;
    }

    /**设置出生日期*/
    public void setEmBirthday(Date emBirthday) {
        this.emBirthday = emBirthday;
    }

    /**获取入职日期 */
    public Date getEmHiredate() {
        return emHiredate;
    }

    /**设置入职日期*/
    public void setEmHiredate(Date emHiredate) {
        this.emHiredate = emHiredate;
    }

    /**获取工资 */
    public Double getEmSalary() {
        return emSalary;
    }

    /**设置工资*/
    public void setEmSalary(Double emSalary) {
        this.emSalary = emSalary;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", emId=").append(emId);
        sb.append(", emName=").append(emName);
        sb.append(", emSex=").append(emSex);
        sb.append(", emBirthday=").append(emBirthday);
        sb.append(", emHiredate=").append(emHiredate);
        sb.append(", emSalary=").append(emSalary);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}