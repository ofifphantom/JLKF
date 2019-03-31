package com.jl.mis.mapper;

import com.jl.mis.model.entity.UserJurisdictionRelationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserJurisdictionRelationMapper {
    /**
     * 根据用户主键id查询菜单
     * @param userTypeId 用户id
     * @return 菜单列表
     */
    List<UserJurisdictionRelationEntity> listUserJurisdictionByUserTypeId(@Param("userTypeId") Integer userTypeId);
}
