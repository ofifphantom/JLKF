package com.jl.mis.service.impl;

import com.jl.mis.dto.MenuDTO;
import com.jl.mis.mapper.MenuDetailMapper;
import com.jl.mis.mapper.UserJurisdictionRelationMapper;
import com.jl.mis.model.entity.MenuDetailEntity;
import com.jl.mis.model.entity.UserJurisdictionRelationEntity;
import com.jl.mis.service.UserJurisdictionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作权限实现类
 */
@Service
public class UserJurisdictionRelationServiceImpl implements UserJurisdictionRelationService {
    @Autowired
    private UserJurisdictionRelationMapper userJurisdictionRelationMapper;
    @Autowired
    private MenuDetailMapper menuDetailMapper;
    @Override
    public List<MenuDTO> listMenuByUserTypeId(Integer userTypeId) {
        List<MenuDTO> menuDTO = null;
        if(null != userTypeId && userTypeId > 0){
            List<UserJurisdictionRelationEntity> jurisdictionRelationEntityList = userJurisdictionRelationMapper.listUserJurisdictionByUserTypeId(userTypeId);
            String ids = convertListToString(jurisdictionRelationEntityList);
            List<MenuDetailEntity> menuDetailEntityList = menuDetailMapper.listMenuById(ids);
            menuDTO = convertToMenuDTO(menuDetailEntityList);
        }
        return menuDTO;
    }


    /**
     * 将菜单id拼接成String
     * @param jurisdictionRelationEntityList 得到的权限关系
     * @return 拼接好的菜单 id
     */
    private String convertListToString(List<UserJurisdictionRelationEntity> jurisdictionRelationEntityList){
        String ids = "";
        if(jurisdictionRelationEntityList.size() > 1){
            ids = jurisdictionRelationEntityList.get(0).getMenuDetailId().toString();
            for(int i = 1;i < jurisdictionRelationEntityList.size();i++){
                ids = ids + "," + jurisdictionRelationEntityList.get(i).getMenuDetailId();
            }
        }else if (jurisdictionRelationEntityList.size() > 0){
            ids = jurisdictionRelationEntityList.get(0).getMenuDetailId().toString();
        }
        return ids;
    }
    /**
     * 将菜单转换为树形菜单
     * @param menuDetailEntityList 菜单
     * @return 树形菜单
     */
    private List<MenuDTO> convertToMenuDTO(List<MenuDetailEntity> menuDetailEntityList){
        List<MenuDTO> list = null;
        if(menuDetailEntityList.size() > 0){
            list = new ArrayList<>();
            // 循环得到菜单标题
            for(int i = 0;i < menuDetailEntityList.size();i++){
                MenuDetailEntity menuDetailEntity = menuDetailEntityList.get(i);
                // parentId = 0 表示当前为最大菜单
                if(menuDetailEntity.getMenuParentId() == 0){
                    MenuDTO menuDTO = new MenuDTO();
                    menuDTO.setId(menuDetailEntity.getId());
                    menuDTO.setUrl(menuDetailEntity.getMenuUrl());
                    menuDTO.setText(menuDetailEntity.getMenuName());
                    menuDTO.setIconUrl(menuDetailEntity.getMenuIconUrl());
                    list.add(menuDTO);
                }
            }
            // 根据得到的菜单标题查找与之对应的子项
            for(int j = 0;j < list.size();j++){
                // 得到菜单标题
                MenuDTO menuDTO = list.get(j);
                // 用于存储菜单子项
                List<MenuDTO> menuDTOArrayList = new ArrayList<>();
                // 判断该菜单下是否有子项
                Integer resultIndex = getListSise(menuDTO.getId(),menuDetailEntityList);
               if(resultIndex > 0){
                   // 用于记录菜单的子项已经保存了多少个
                   int index = 0;
                   // 循环菜单项目
                   for(int k = 0;k < menuDetailEntityList.size();k++){
                       // 得到菜单子项
                       MenuDetailEntity menuDetailEntity = menuDetailEntityList.get(k);
                       // 判断得到的菜单子项是否属于菜单标题
                       if(menuDetailEntity.getMenuParentId() == menuDTO.getId()){
                           // 存储菜单子项
                           MenuDTO menuDTO1 = new MenuDTO();
                           menuDTO1.setId(menuDetailEntity.getId());
                           menuDTO1.setUrl(menuDetailEntity.getMenuUrl());
                           menuDTO1.setText(menuDetailEntity.getMenuName());
                           menuDTO1.setIconUrl(menuDetailEntity.getMenuIconUrl());
                           menuDTOArrayList.add(menuDTO1);
                           // 标识加 1
                           index++;
                       }
                       // 如果菜单子项以经满足
                       if(index == resultIndex) {
                           // 存储菜单子项
                           menuDTO.setChildren(menuDTOArrayList);
                           break;
                       }
                   }
               }
            }
        }
        return list;
    }

    /**
     * 获取该菜单下是否有子项
     * @param topicId 标题菜单id
     * @param menuDetailEntityList 菜单列表
     * @return 子项数量
     */
    private int getListSise(Integer topicId,List<MenuDetailEntity> menuDetailEntityList){
        Integer index = Integer.valueOf(0);
        for(int i = 0;i < menuDetailEntityList.size();i++){
            if(topicId == menuDetailEntityList.get(i).getMenuParentId()){
                index++;
            }
        }
        return index;
    }

}
