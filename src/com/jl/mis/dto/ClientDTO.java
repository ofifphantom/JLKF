package com.jl.mis.dto;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 咨询记录返回类
 * @Date 2018-5-29
 */
public class ClientDTO {
    // 主键 id
    private Integer id;

    // 用户名字(手机号)
    private String userName;
    // 是否 vip
    private String isVip;
    // 咨询编号
    private String consultNumber;
    // 咨询类型(售后咨询用)
    private String consultType;
    // 客服名称
    private String serviceName;
    // 开始时间
    private String createTime;
    // 咨询时长
    private String consultTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getConsultNumber() {
        return consultNumber;
    }

    public void setConsultNumber(String consultNumber) {
        this.consultNumber = consultNumber;
    }

    public String getConsultType() {
        return consultType;
    }

    public void setConsultType(String consultType) {
        this.consultType = consultType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getConsultTime() {
        return consultTime;
    }

    public void setConsultTime(String consultTime) {
        if(null != consultTime){
            this.consultTime = consultTime;
        }else {
            this.consultTime = "00:00:00";
        }
    }
}
