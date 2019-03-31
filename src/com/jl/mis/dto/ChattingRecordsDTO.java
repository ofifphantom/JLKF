package com.jl.mis.dto;

import com.jl.mis.model.entity.ChattingRecordsEntity;

import java.util.List;

/**
 * 聊天详情返回
 */
public class ChattingRecordsDTO {
    private String consultNumber;
    /**
     * 用户名称(手机号)
     */
    private String userName;
    /**
     * 是否 vip
     */
    private String isVip;
    /**
     * 客服登录名称
     */
    private String serviceLoginName;
    /**
     * 客服名称
     */
    private String serviceName;
    /**
     * 进入队列时间
     */
    private String enterQueueTime;
    /**
     * 等待时间
     */
    private String awaitTime;
    /**
     * 聊天开始时间
     */
    private String entranceTime;
    /**
     * 聊天结束时间
     */
    private String finishTime;
    /**
     * 咨询时间
     */
    private String consultTime;
    /**
     * 聊天信息集合
     */
    private List<ChattingDTO> chattingRecordsEntityList;

    public String getConsultNumber() {
        return consultNumber;
    }

    public void setConsultNumber(String consultNumber) {
        this.consultNumber = consultNumber;
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

    public String getServiceLoginName() {
        return serviceLoginName;
    }

    public void setServiceLoginName(String serviceLoginName) {
        this.serviceLoginName = serviceLoginName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEnterQueueTime() {
        return enterQueueTime;
    }

    public void setEnterQueueTime(String enterQueueTime) {
        this.enterQueueTime = enterQueueTime;
    }

    public String getAwaitTime() {
        return awaitTime;
    }

    public void setAwaitTime(String awaitTime) {
        this.awaitTime = awaitTime;
    }

    public String getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(String entranceTime) {
        this.entranceTime = entranceTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getConsultTime() {
        return consultTime;
    }

    public void setConsultTime(String consultTime) {
        this.consultTime = consultTime;
    }

    public List<ChattingDTO> getChattingRecordsEntityList() {
        return chattingRecordsEntityList;
    }

    public void setChattingRecordsEntityList(List<ChattingDTO> chattingRecordsEntityList) {
        this.chattingRecordsEntityList = chattingRecordsEntityList;
    }
}
