package com.jl.mis.mapper;


import com.jl.mis.model.entity.HabitLanguageTypeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/22 10:34
 * 常用语类型 数据库映射
 */
@Repository
public interface HabitLanguageTypeMapper {
    /**
     * 查询用户下的常用语类型
     * @param provider_id 当前登陆的客服id
     * @return
     */
    List<HabitLanguageTypeEntity> selectHabitLanguageType(@Param("provider_id") int provider_id);

    /**
     * 删除常用语类型
     * @param id 常用语类型id
     */
    void delHabitLanguageType(@Param("id") int id);

    /**
     * 增加常用语类型
     * @param habitLanguageTypeEntity
     */
    void insertHabitLanguageType(@Param("HabitLanguageTypeEntity") HabitLanguageTypeEntity habitLanguageTypeEntity);

    /**
     * 修改常用语类型表 根据id修改
     * @param habitLanguageTypeEntity 常用语类型
     */
    void updateHabitLanguageRelation(@Param("HabitLanguageTypeEntity") HabitLanguageTypeEntity habitLanguageTypeEntity);
    }
