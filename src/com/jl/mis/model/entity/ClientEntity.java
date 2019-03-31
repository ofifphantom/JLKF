package com.jl.mis.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class ClientEntity implements Serializable {
    private Integer id;
    private Integer isVip;
    private String userName;
    private String loginName;
    private Integer audienceId;
    private Integer state;
    private String consultNumber;
    private String awaitTime;
    private Timestamp entranceTime;
    private String consultTime;
    private Timestamp finishTime;
    private Timestamp enterQueue;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(Integer audienceId) {
        this.audienceId = audienceId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getConsultNumber() {
        return consultNumber;
    }

    public void setConsultNumber(String consultNumber) {
        this.consultNumber = consultNumber;
    }

    public String getAwaitTime() {
        return awaitTime;
    }

    public void setAwaitTime(String awaitTime) {
        this.awaitTime = awaitTime;
    }

    public Timestamp getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Timestamp entranceTime) {
        this.entranceTime = entranceTime;
    }

    public String getConsultTime() {
        return consultTime;
    }

    public void setConsultTime(String consultTime) {
        this.consultTime = consultTime;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public Timestamp getEnterQueue() {
        return enterQueue;
    }

    public void setEnterQueue(Timestamp enterQueue) {
        this.enterQueue = enterQueue;
    }

}
