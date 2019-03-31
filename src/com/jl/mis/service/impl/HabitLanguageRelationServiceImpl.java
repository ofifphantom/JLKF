package com.jl.mis.service.impl;

import com.jl.mis.dto.HabitLanguageReationDto;
import com.jl.mis.dto.HabitLanguageReations;
import com.jl.mis.dto.TreeChildrenDTO;
import com.jl.mis.dto.TreeJsonDTO;
import com.jl.mis.mapper.HabitLanguageRelationMapper;
import com.jl.mis.mapper.HabitLanguageTypeMapper;
import com.jl.mis.model.entity.HabitLanguageRelationType;
import com.jl.mis.model.entity.HabitLanguageRelationEntity;
import com.jl.mis.model.entity.HabitLanguageTypeEntity;
import com.jl.mis.service.HabitLanguageRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用语 业务实现类
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/22 11:35
 */
@Service
public class HabitLanguageRelationServiceImpl implements HabitLanguageRelationService {
    @Autowired
    private HabitLanguageTypeMapper habitLanguageTypeMapper;
    @Autowired
    private HabitLanguageRelationMapper habitLanguageRelationMapper;

    @Override
    public List<HabitLanguageTypeEntity> insertHabitLanguageType(HabitLanguageTypeEntity habitLanguageTypeEntity) {
        //添加数据
        habitLanguageTypeMapper.insertHabitLanguageType(habitLanguageTypeEntity);
        return selectLanguageType(habitLanguageTypeEntity.getProviderId());
    }

    @Override
    public List<HabitLanguageTypeEntity> delHabitLanguageType(int provider_id, int id) {
        //删除常用语类型下的所有列
        habitLanguageRelationMapper.delHabitLanguageRelationToTypeId(id);
        //删除常用语类型
        habitLanguageTypeMapper.delHabitLanguageType(id);
        //查看当前用户下所有常用语类型
        List<HabitLanguageTypeEntity> habitLanguageTypeEntities= selectLanguageType(provider_id);
        return habitLanguageTypeEntities;
    }

    @Override
    public List<HabitLanguageReationDto> updateHabitLanguageType(HabitLanguageTypeEntity habitLanguageTypeEntity) {
        //根据id修改常用语类型
        habitLanguageTypeMapper.updateHabitLanguageRelation(habitLanguageTypeEntity);
        //查看当前用户下所有常用语记录
        List<HabitLanguageRelationType> habitLanguageRelationTypes = habitLanguageRelationMapper.selectAllToHabitLanguageRelation(habitLanguageTypeEntity.getProviderId());
        return convertToDto(habitLanguageRelationTypes);
    }

    @Override
    public List<HabitLanguageRelationEntity> delHabitLanguage(int id, int provider_id) {
        //根据id删除常用语
        habitLanguageRelationMapper.delHabitLanguageRelation(id);
        //查看当前用户下所有常用语记录
        List<HabitLanguageRelationEntity> habitLanguageRelationEntities= selectHabitLanguageRelation(provider_id);
        return habitLanguageRelationEntities;
    }

    @Override
    public List<HabitLanguageRelationEntity> insertHabitLanguage(int provider_id, HabitLanguageRelationEntity habitLanguageRelationEntity) {

        //添加一条常用语
        habitLanguageRelationMapper.insertHabitLanguageRelationToType(habitLanguageRelationEntity);



       List<HabitLanguageRelationEntity> habitLanguageRelationEntities=selectHabitLanguageRelation(provider_id);


        return habitLanguageRelationEntities;
    }

    @Override
    public List<HabitLanguageReationDto> updateHabitLanguage(int provider_id, HabitLanguageRelationEntity habitLanguageRelationEntity) {
        //修改一条常用语
        habitLanguageRelationMapper.updateHabitLanguageRelationToid(habitLanguageRelationEntity);
        //查看当前用户下所有常用语记录
        List<HabitLanguageRelationType> habitLanguageRelationTypes = habitLanguageRelationMapper.selectAllToHabitLanguageRelation(provider_id);
        return convertToDto(habitLanguageRelationTypes);
    }

    @Override
    public List<HabitLanguageReationDto> selectAll(int provider_id) {
        //查看当前用户下所有常用语记录
        List<HabitLanguageRelationType> habitLanguageRelationTypes = habitLanguageRelationMapper.selectAllToHabitLanguageRelation(provider_id);
        return convertToDto(habitLanguageRelationTypes);
    }
    @Override
    public List<TreeJsonDTO> ConvertTreeJsonDto(List<HabitLanguageReationDto> habitLanguageReationDtos) {
        List<TreeJsonDTO> treeJsonDTOS = new ArrayList<>();
        for (int i = 0; i < habitLanguageReationDtos.size(); i++) {
            TreeJsonDTO treeJsonDTO = new TreeJsonDTO();
            List<TreeChildrenDTO> children = new ArrayList<>();
            HabitLanguageReationDto habitLanguageReationDto = habitLanguageReationDtos.get(i);
            //常用语类型id
            treeJsonDTO.setId(habitLanguageReationDto.getTypeid());
            //常用语名字
            treeJsonDTO.setText(habitLanguageReationDto.getHabit_language_type_name());
            treeJsonDTO.setState("open");

            List<HabitLanguageReations> habitLanguageReations = habitLanguageReationDto.getHabitLanguageReations();

            for (int j = 0; j < habitLanguageReations.size(); j++) {
                HabitLanguageReations habitLanguageReations1 = habitLanguageReations.get(j);
                TreeChildrenDTO treeChildrenDTO = new TreeChildrenDTO();
                treeChildrenDTO.setId(habitLanguageReations1.getId());
                treeChildrenDTO.setText(habitLanguageReations1.getHabitlanguage_content());
                children.add(treeChildrenDTO);
            }
            treeJsonDTO.setChildren(children);
            treeJsonDTOS.add(treeJsonDTO);
        }

        return treeJsonDTOS;
    }

    @Override
    public List<HabitLanguageTypeEntity> selectLanguageType(int provider_id) {
        return habitLanguageTypeMapper.selectHabitLanguageType(provider_id);
    }

    @Override
    public List<HabitLanguageRelationEntity> selectHabitLanguageRelation(int provider_id) {
        List<HabitLanguageTypeEntity> habitLanguageTypeEntities= habitLanguageTypeMapper.selectHabitLanguageType(provider_id);
        List<HabitLanguageRelationEntity> languageRelationEntities=new ArrayList<>();
        for (int i=0;i<habitLanguageTypeEntities.size();i++){
           int id= habitLanguageTypeEntities.get(i).getId();
           List<HabitLanguageRelationEntity> habitLanguageRelationEntities=habitLanguageRelationMapper.selectHabitLanguageRelationToType(id);
        for (int j=0;j<habitLanguageRelationEntities.size();j++){
            HabitLanguageRelationEntity habitLanguageRelationEntity1=new HabitLanguageRelationEntity();
            HabitLanguageRelationEntity habitLanguageRelationEntity=  habitLanguageRelationEntities.get(j);
            habitLanguageRelationEntity1.setHabitlanguageContent(habitLanguageRelationEntity.getHabitlanguageContent());
            habitLanguageRelationEntity1.setId(habitLanguageRelationEntity.getId());
            languageRelationEntities.add(habitLanguageRelationEntity1);
        }
        }
        return languageRelationEntities;
    }

    @Override
    public List<HabitLanguageRelationEntity> selectHabitLanguageRelationToType(int typeId) {
        List<HabitLanguageRelationEntity> habitLanguageRelationEntities= habitLanguageRelationMapper.selectHabitLanguageRelationToType(typeId);
        return habitLanguageRelationEntities;
    }

    @Override
    public List<HabitLanguageReationDto> convertToDto(List<HabitLanguageRelationType> languageRelationTypes) {
        //创建标识符来判断typeid是否变过
        int typeids = 0;
        //创建标识符记录详情 集合中的集合下标
        int identifier = -1;
        //创建集合记录当下使用过的typeids 防止id重复 重复添加
        List<Integer> list = new ArrayList<>();
        if (languageRelationTypes == null) {
            try {
                throw new Exception("出错了传入参数不正确");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回对象空集合
        List<HabitLanguageReationDto> habitLanguageReationDtos = new ArrayList<HabitLanguageReationDto>();
        //对象中包含集合
        HabitLanguageReationDto habitLanguageReationDto;
        //遍历原有集合
        for (int i = 0; i < languageRelationTypes.size(); i++) {
            //创建包含集合对象
            habitLanguageReationDto = new HabitLanguageReationDto();
            //记录标识符,记录标识符list集合中相同数字的个数
            int numbers = 0;
            //获取一个有值集合的下标languageRelationTypes
            HabitLanguageRelationType habitLanguageRelationType = languageRelationTypes.get(i);
            //如果标识符id 等于0 或者不等于当前typeid
            if (typeids == 0 || typeids != habitLanguageRelationType.getTypeid()) {

                //将typeId 传到标识符中
                typeids = habitLanguageRelationType.getTypeid();
                //添加List
                list.add(typeids);
                //遍历list 判断这个集合中有没有相同的key 如果有代表 以前已经存过
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j) == typeids) {
                        numbers++;
                    }
                }
                //判断以前是否存过
                if (numbers < 2) {
                    //如果没有存过 添加属性 并且添加对象集合
                    habitLanguageReationDto.setHabit_language_type_name(habitLanguageRelationType.getHabit_language_type_name());
                    habitLanguageReationDto.setTypeid(habitLanguageRelationType.getTypeid());
                    habitLanguageReationDto.setProvider_id(habitLanguageRelationType.getProvider_id());
                    habitLanguageReationDtos.add(habitLanguageReationDto);
                    //下标等于当前typeid-1 因为typeId是从1 开始的 而数组下标从0开始
                    identifier = habitLanguageReationDtos.size()-1;
                }

                for (int k=0;k<habitLanguageReationDtos.size();k++){
                    int id= habitLanguageReationDtos.get(k).getTypeid();
                    if (id== habitLanguageRelationType.getTypeid()){
                        //如果存过 添加对象中的集合
                        HabitLanguageReations habitLanguageReations = new HabitLanguageReations();
                        habitLanguageReations.setHabitlanguage_content(habitLanguageRelationType.getHabitlanguage_content());
                        habitLanguageReations.setId(habitLanguageRelationType.getId());
                        habitLanguageReationDtos.get(k).getHabitLanguageReations().add(habitLanguageReations);
                    }
                }

            } else {
                for (int l=0;l<habitLanguageReationDtos.size();l++){
                    int id= habitLanguageReationDtos.get(l).getTypeid();
                    if (id== habitLanguageRelationType.getTypeid()){
                        //如果存过 添加对象中的集合
                        System.out.println();
                        HabitLanguageReations habitLanguageReations = new HabitLanguageReations();
                        habitLanguageReations.setHabitlanguage_content(habitLanguageRelationType.getHabitlanguage_content());
                        habitLanguageReations.setId(habitLanguageRelationType.getId());
                        habitLanguageReationDtos.get(l).getHabitLanguageReations().add(habitLanguageReations);
                    }
                } }
        }
        return habitLanguageReationDtos;
    }

}
