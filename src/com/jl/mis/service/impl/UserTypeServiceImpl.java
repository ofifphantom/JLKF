package com.jl.mis.service.impl;

import com.jl.mis.mapper.UserTypeMapper;
import com.jl.mis.model.entity.UserTypeEntity;
import com.jl.mis.service.UserTypeService;
import com.jl.mis.utils.GetSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.jl.mis.utils.GetSessionUtil.GetSessionUserTypeId;

/**
 * 操作用户类型实现类
 *
 * @date 2018-5-20
 */
@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    private UserTypeMapper userTypeMapper;
    @Autowired
    private UserServiceUtil userServiceUtil;
    @Override
    public List<UserTypeEntity> listUserTypeByUserTypeId(HttpServletRequest request) {
        int loginUserTypeId = GetSessionUtil.GetSessionUserTypeId(request);
        List<UserTypeEntity> userTypeEntityList = userTypeMapper.listUserTypeByUserTypeId(userServiceUtil.getUserTypeId(loginUserTypeId));
        return userTypeEntityList;
    }

    @Override
    public List<UserTypeEntity> listUserTypeAll() {
        return userTypeMapper.listUserTypeAll();
    }
}
