package com.jl.mis.dto;

import java.sql.Timestamp;

/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/23 11:14
 * 操作日志返回值类
 */
public class OperatingRecordDTO {
    //操作id
    private int id;
    private Integer showOperatingRecord;
    //操作介绍
    private String operatingIntroduce;
    //操作类型 如 售前 售后
    private String operatingModel;
    //操作人姓名
    private String providerName;
    //操作人昵称
    private String provideRnickName;
    //操作时间
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getShowOperatingRecord() {
        return showOperatingRecord;
    }

    public void setShowOperatingRecord(Integer showOperatingRecord) {
        this.showOperatingRecord = showOperatingRecord;
    }

    public String getOperatingIntroduce() {
        return operatingIntroduce;
    }

    public void setOperatingIntroduce(String operatingIntroduce) {
        this.operatingIntroduce = operatingIntroduce;
    }

    public String getOperatingModel() {
        return operatingModel;
    }

    public void setOperatingModel(String operatingModel) {
        this.operatingModel = operatingModel;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProvideRnickName() {
        return provideRnickName;
    }

    public void setProvideRnickName(String provideRnickName) {
        this.provideRnickName = provideRnickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
