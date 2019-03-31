package com.jl.mis.mapper;

import com.jl.mis.model.entity.HabitLanguageRelationType;
import com.jl.mis.model.entity.HabitLanguageRelationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 常用语表 mapper
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/22 10:31
 */
@Repository
public interface HabitLanguageRelationMapper {
    /**
     * 根据常用语类型查询类型下的所有的常用语
     *
     * @param habit_language_type_id 常用语类型
     * @return 返回常用语集合
     */
    List<HabitLanguageRelationEntity> selectHabitLanguageRelationToType(@Param("habit_language_type_id") int habit_language_type_id);

    /**
     * 在哪个类型下 添加常用语
     *
     * @param habitLanguageRelationEntity 常用语实体类
     */
    void insertHabitLanguageRelationToType(@Param("HabitLanguageRelationEntity") HabitLanguageRelationEntity habitLanguageRelationEntity);

    /**
     * 删除常用语
     *
     * @param id 常用语表id
     */
    void delHabitLanguageRelation(@Param("id") int id);

    /**
     * 根据类型id删除 类型下的所有常用语
     *
     * @param habit_language_type_id 常用语类型id
     */
    void delHabitLanguageRelationToTypeId(@Param("habit_language_type_id") int habit_language_type_id);

    /**
     * 根据常用语id修改常用语表
     *
     * @param habitLanguageRelationEntity 常用语实体类
     */
    void updateHabitLanguageRelationToid(@Param("HabitLanguageRelationEntity") HabitLanguageRelationEntity habitLanguageRelationEntity);

    /**
     * 查询当前客服下的 所有的类型 和类型下的常用语
     *
     * @return 常用语类型集合
     */
    List<HabitLanguageRelationType> selectAllToHabitLanguageRelation(@Param("provider_id") int provider_id);
}
