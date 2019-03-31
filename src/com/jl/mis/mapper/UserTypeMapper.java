package com.jl.mis.mapper;

import com.jl.mis.model.entity.UserTypeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户类型表操作
 * @date 2018-5-20 PM 17:17
 */
public interface UserTypeMapper {
    /**
     * 根据用户类型 id 获取用户类型
     * @return 用户类型列表
     */
    List<UserTypeEntity> listUserTypeByUserTypeId(@Param("userTypeId") String userTypeId);

    /**
     * 查询所有用户
     * @return 用户类型列表
     */
    List<UserTypeEntity> listUserTypeAll();
}
