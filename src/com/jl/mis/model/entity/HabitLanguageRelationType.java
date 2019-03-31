package com.jl.mis.model.entity;

import java.io.Serializable;

/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/23 9:52
 * 用做展示所有的常用语
 */
public class HabitLanguageRelationType implements Serializable {
    //类型id
    private Integer typeid;
    //类型名称
    private String habit_language_type_name;
    //所属客户id
    private Integer provider_id;
    //客服常用语id
    private Integer id;
    //常用语
    private String habitlanguage_content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHabitlanguage_content() {
        return habitlanguage_content;
    }

    public void setHabitlanguage_content(String habitlanguage_content) {
        this.habitlanguage_content = habitlanguage_content;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getHabit_language_type_name() {
        return habit_language_type_name;
    }

    public void setHabit_language_type_name(String habit_language_type_name) {
        this.habit_language_type_name = habit_language_type_name;
    }

    public Integer getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(Integer provider_id) {
        this.provider_id = provider_id;
    }
}
