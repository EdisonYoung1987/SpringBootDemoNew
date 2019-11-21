package com.edison.springbootdemo.domain;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**员工信息表实体类*/
@Mapper
@Repository
public class EmInfo implements Serializable {
    /**
     * 员工号
     */
    private Integer emId;

    /**
     * 名称
     */
    private String emName;

    /**
     * 性别
     */
    private String emSex;

    /**
     * 生日
     */
    private Date emBirthday;

    /**
     * 入职日期
     */
    private Date emHiredate;

    /**
     * 薪资
     */
    private BigDecimal emSalary;

    private static final long serialVersionUID = 1L;

    /**获取员工号 */
    public Integer getEmId() {
        return emId;
    }

    /**设置员工号*/
    public void setEmId(Integer emId) {
        this.emId = emId;
    }

    /**获取名称 */
    public String getEmName() {
        return emName;
    }

    /**设置名称*/
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

    /**获取生日 */
    public Date getEmBirthday() {
        return emBirthday;
    }

    /**设置生日*/
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

    /**获取薪资 */
    public BigDecimal getEmSalary() {
        return emSalary;
    }

    /**设置薪资*/
    public void setEmSalary(BigDecimal emSalary) {
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