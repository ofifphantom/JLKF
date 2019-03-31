package com.jl.mis.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class OperatingRecordEntity implements Serializable {
    private Integer id;
    private String operatingIntroduce;
    private Integer operatingModelTypeId;
    private Integer providerId;
    private Timestamp createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperatingIntroduce() {
        return operatingIntroduce;
    }

    public void setOperatingIntroduce(String operatingIntroduce) {
        this.operatingIntroduce = operatingIntroduce;
    }

    public Integer getOperatingModelTypeId() {
        return operatingModelTypeId;
    }

    public void setOperatingModelTypeId(Integer operatingModelTypeId) {
        this.operatingModelTypeId = operatingModelTypeId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


}
