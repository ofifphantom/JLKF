package com.jl.mis.service;

import com.jl.mis.dto.HabitLanguageReationDto;
import com.jl.mis.dto.TreeJsonDTO;
import com.jl.mis.model.entity.HabitLanguageRelationType;
import com.jl.mis.model.entity.HabitLanguageRelationEntity;
import com.jl.mis.model.entity.HabitLanguageTypeEntity;

import java.util.List;

/**
 * 常用语业务逻辑
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/22 11:34
 */
public interface HabitLanguageRelationService {
    /**
     * 添加类型 并做展示
     *
     * @param habitLanguageTypeEntity 常用语实体类
     * @return 所有常用语
     */
    List<HabitLanguageTypeEntity> insertHabitLanguageType(HabitLanguageTypeEntity habitLanguageTypeEntity);

    /**
     * 删除常用语类型 并且展示
     *
     * @param provider_id 客服id
     * @param id          对应的常用语id
     * @return 所有常用语
     */
    List<HabitLanguageTypeEntity> delHabitLanguageType(int provider_id, int id);


    /**
     * 根据id修改常用语言类型
     *
     * @param habitLanguageTypeEntity 常用于对象
     * @return 所有常用语
     */
    List<HabitLanguageReationDto> updateHabitLanguageType(HabitLanguageTypeEntity habitLanguageTypeEntity);

    /**
     * 删除常用语
     *
     * @param id          常用语id
     * @param provider_id 当前登陆的客服id
     * @return 所有常用语
     */
    List<HabitLanguageRelationEntity> delHabitLanguage(int id, int provider_id);

    /**
     * 添加常用语
     *
     * @param habitLanguageRelationEntity 常用语实体类
     * @param provider_id                 当前登陆的客服id
     * @return 所有常用语
     */
    List<HabitLanguageRelationEntity> insertHabitLanguage(int provider_id, HabitLanguageRelationEntity habitLanguageRelationEntity);

    /**
     * 修改常用语 根据常用语id
     *
     * @param habitLanguageRelationEntity 常用语实体类
     * @param provider_id                 当前登陆的客服id
     * @return 所有常用语
     */
    List<HabitLanguageReationDto> updateHabitLanguage(int provider_id, HabitLanguageRelationEntity habitLanguageRelationEntity);

    /**
     * 将集合转换为前台可读的集合包集合
     *
     * @param languageRelationTypes 两表查询集合
     * @return
     */
    List<HabitLanguageReationDto> convertToDto(List<HabitLanguageRelationType> languageRelationTypes);

    /**
     * 查看当前所有用户的常用语
     * @param provider_id 当前登陆的用户id
     * @return
     */
    List<HabitLanguageReationDto> selectAll(int provider_id);

    /**
     * 将集合转换为树形菜单属性
     * @param habitLanguageReationDtos 常用语集合
     * @return 属性菜单json 属性
     */
    List<TreeJsonDTO> ConvertTreeJsonDto(List<HabitLanguageReationDto> habitLanguageReationDtos);

    /**
     * 根据当前登陆账号查询所有的类型
     * @param provider_id 登陆账号id
     * @return
     */
    List<HabitLanguageTypeEntity> selectLanguageType(int provider_id);

    /**
     * 查询当前用户下的所有常用语
     * @param provider_id
     * @return
     */
    List<HabitLanguageRelationEntity> selectHabitLanguageRelation(int provider_id);

    /**
     * 根据常用语类型id查询当前分类的所有常用语
     *
     * @param typeId 类型id
     * @return 常用语
     */
    List<HabitLanguageRelationEntity> selectHabitLanguageRelationToType(int typeId);

}
