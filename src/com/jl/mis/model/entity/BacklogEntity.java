package com.jl.mis.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class BacklogEntity implements Serializable {
    private Integer id;
    private String backlogName;
    private Integer submitter;
    private Integer submissionTime;
    private Integer approverId;
    private String itemType;
    private Integer providerId;
    private String orderMessage;
    private Integer preSalesTypeId;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBacklogName() {
        return backlogName;
    }

    public void setBacklogName(String backlogName) {
        this.backlogName = backlogName;
    }

    public Integer getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Integer submitter) {
        this.submitter = submitter;
    }

    public Integer getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Integer submissionTime) {
        this.submissionTime = submissionTime;
    }

    public Integer getApproverId() {
        return approverId;
    }

    public void setApproverId(Integer approverId) {
        this.approverId = approverId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public Integer getPreSalesTypeId() {
        return preSalesTypeId;
    }

    public void setPreSalesTypeId(Integer preSalesTypeId) {
        this.preSalesTypeId = preSalesTypeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
