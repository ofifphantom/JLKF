package com.jl.mis.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class HabitLanguageTypeEntity implements Serializable {
    private Integer id;
    private String habitLanguageTypeName;
    private Integer providerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHabitLanguageTypeName() {
        return habitLanguageTypeName;
    }

    public void setHabitLanguageTypeName(String habitLanguageTypeName) {
        this.habitLanguageTypeName = habitLanguageTypeName;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }


}
