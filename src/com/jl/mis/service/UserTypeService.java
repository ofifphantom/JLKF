package com.jl.mis.service;

import com.jl.mis.model.entity.UserTypeEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 操作用户类型Service
 * @date 2018-5-20
 */
public interface UserTypeService {
    /**
     * 根据条件获取用户类型
     * @param request
     * @return 用户类型列表
     */
    List<UserTypeEntity> listUserTypeByUserTypeId(HttpServletRequest request);
    /**
     * 获取所有用户类型
     * @return 用户类型列表
     */
    List<UserTypeEntity> listUserTypeAll();
}
