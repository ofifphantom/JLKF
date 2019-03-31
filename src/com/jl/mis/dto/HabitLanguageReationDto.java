package com.jl.mis.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/23 13:31
 * 展示常用语返回值
 */
public class HabitLanguageReationDto {
    //类型id
    private int typeid;
    //类型名称
    private String habit_language_type_name;
    //所属客户id
    private int provider_id;
    //类型下的所有常用语
    List<HabitLanguageReations> habitLanguageReations=new ArrayList<>();

    public List<HabitLanguageReations> getHabitLanguageReations() {
        return habitLanguageReations;
    }

    public void setHabitLanguageReations(List<HabitLanguageReations> habitLanguageReations) {
        this.habitLanguageReations = habitLanguageReations;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getHabit_language_type_name() {
        return habit_language_type_name;
    }

    public void setHabit_language_type_name(String habit_language_type_name) {
        this.habit_language_type_name = habit_language_type_name;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }
}
