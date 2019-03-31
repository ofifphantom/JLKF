package com.jl.mis.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class HabitLanguageRelationEntity implements Serializable {
    private Integer id;
    private Integer habitLanguageTypeId;
    private String habitlanguageContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHabitLanguageTypeId() {
        return habitLanguageTypeId;
    }

    public void setHabitLanguageTypeId(Integer habitLanguageTypeId) {
        this.habitLanguageTypeId = habitLanguageTypeId;
    }

    public String getHabitlanguageContent() {
        return habitlanguageContent;
    }

    public void setHabitlanguageContent(String habitlanguageContent) {
        this.habitlanguageContent = habitlanguageContent;
    }


}
