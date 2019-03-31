package com.jl.mis.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class ReceptionRecordEntity implements Serializable {
    private Integer id;
    private String receptionNumber;
    private Timestamp receptionTime;
    private String receptionRmark;
    private String receptionFileUrl;
    private Integer providerId;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceptionNumber() {
        return receptionNumber;
    }

    public void setReceptionNumber(String receptionNumber) {
        this.receptionNumber = receptionNumber;
    }

    public Timestamp getReceptionTime() {
        return receptionTime;
    }

    public void setReceptionTime(Timestamp receptionTime) {
        this.receptionTime = receptionTime;
    }

    public String getReceptionRmark() {
        return receptionRmark;
    }

    public void setReceptionRmark(String receptionRmark) {
        this.receptionRmark = receptionRmark;
    }

    public String getReceptionFileUrl() {
        return receptionFileUrl;
    }

    public void setReceptionFileUrl(String receptionFileUrl) {
        this.receptionFileUrl = receptionFileUrl;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
