package com.jl.mis.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class OperatingModelEntity implements Serializable {
    private int id;
    private String operatingModelName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperatingModelName() {
        return operatingModelName;
    }

    public void setOperatingModelName(String operatingModelName) {
        this.operatingModelName = operatingModelName;
    }


}
