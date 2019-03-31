package com.jl.mis.service;

import com.jl.mis.dto.MenuDTO;

import java.util.List;

/**
 * 操作用户权限Service
 * @date 2018-5-24
 */
public interface UserJurisdictionRelationService {
    /**
     * 根据用户类型获取菜单列表
     * @param userTypeId 用户类型
     * @return 菜单列表
     */
    List<MenuDTO> listMenuByUserTypeId(Integer userTypeId);
}
