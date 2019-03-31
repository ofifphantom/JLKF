package com.jl.mis.service.impl;

import com.jl.mis.dto.UserDTO;
import com.jl.mis.mapper.UserManagerMapper;
import com.jl.mis.mapper.UserTypeMapper;
import com.jl.mis.model.entity.OperatingRecordEntity;
import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.model.entity.UserTypeEntity;
import com.jl.mis.service.OperatingRecordService;
import com.jl.mis.service.UserManagerService;
import com.jl.mis.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 操作客服实现类
 * @date 2018-5-20
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {
    @Autowired
    private UserManagerMapper userManagerMapper;
    @Autowired
    private UserTypeMapper userTypeMapper;
    @Autowired
    private UserServiceUtil userServiceUtil;
    @Autowired
    private OperatingRecordService operatingRecordService;

    @Override
    public DataTables listUserAll(HttpServletRequest request) {
        DataTables dataTables = new DataTables();
        PageTools pageTools = new PageTools();
        UserServiceUtil userServiceUtil = new UserServiceUtil();
        pageTools.setQueryParams(" AND user_type_id IN (" + userServiceUtil.getUserTypeId(GetSessionUtil.GetSessionUserTypeId(request)) + ")");
        List<UserEntity> userEntityList = userManagerMapper.listUserByLimit(pageTools.getiDisplayStart(),pageTools.getPageDisplayLength(),pageTools.getQueryParams());
        List<UserTypeEntity> userTypeEntityList = userTypeMapper.listUserTypeAll();
        dataTables.setiTotalDisplayRecords(userManagerMapper.listUserAll().size());
        dataTables.setiTotalRecords(userEntityList.size());
        dataTables.setData(convertUser(userEntityList,userTypeEntityList));
        return dataTables;
    }

    @Override
    public DataTables listUserByCondition(Integer defined, UserEntity userCondition, HttpServletRequest request) {
        DataTables dataTables = new DataTables();
        if(null != userCondition){
            Integer loginUserTypeId = GetSessionUtil.GetSessionUserTypeId(request);
            String queryParams = getUserConditionMap(userCondition,loginUserTypeId);
            Integer startNumber = Integer.valueOf(request.getParameter("start"));
            Integer lengthNumber = Integer.valueOf(request.getParameter("length"));
            List<UserEntity> userEntityList = userManagerMapper.listUserByLimit(startNumber,lengthNumber,queryParams);
            List<UserTypeEntity> userTypeEntityList = userTypeMapper.listUserTypeByUserTypeId(userServiceUtil.getUserTypeId(loginUserTypeId));
            dataTables.setiTotalDisplayRecords(userManagerMapper.listUserByCondition(queryParams).size());
            dataTables.setiTotalRecords(userEntityList.size());
            List<UserDTO> userDTOList = convertUser(userEntityList,userTypeEntityList);
            dataTables.setData(userDTOList);
        }
        return dataTables;
    }

    @Override
    public UserEntity selectUserByLoginName(String loginName) {
        UserEntity userEntity = null;
        if(!loginName.equals("")){
            userEntity = userManagerMapper.getUserByLoginName(loginName);
        }
        return userEntity;
    }

    @Override
    public Integer countUserByLoginName(String loginName) {
        Integer integer = null;
        if(!loginName.equals("")){
            integer = userManagerMapper.countUserByLoginName(loginName);
        }
        return integer;
    }

    @Override
    public UserEntity selectById(Integer userId) {
        UserEntity userEntity = null;
        if(null != userId && userId > 0){
            userEntity = userManagerMapper.getUserByUserId(userId);
        }
        return userEntity;
    }

    @Override
    public String getPassword() {
        Random random = new Random();
        String password = "";
        for(int i = 0;i < 6;i++){
            char c = (char)(int)(Math.random() * 26 + 97);
            password += c;
        }
        password = password + random.nextInt(1000000);
        return password;
    }

    @Override
    public boolean insertUser(HttpServletRequest request, UserEntity userEntity) {
        boolean result = false;
        userEntity = disposeUser(userEntity);
        if (userManagerMapper.insertUser(userEntity) > 0){
            if(inserOperatingRecord(request,userEntity.getUserTypeId(),Constants.OPERATING_INSERT_SERVICE_MSG)){
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean updateUser(HttpServletRequest request, UserEntity userEntity) {
        boolean result = false;
        if(userManagerMapper.updateUserById(disposeUser(userEntity)) > 0){
            if(inserOperatingRecord(request,userManagerMapper.getUserByUserId(userEntity.getId()).getUserTypeId()
                    ,Constants.OPERATING_UPDATE_SERVICE_MSG)){
                result = true;
            }
        }
        return result;
    }

    @Override
    public Integer disableUser(HttpServletRequest request, UserEntity userEntity) {
        Integer result = Integer.valueOf(0);
        if(null != userEntity && userEntity.getId() > 0){
            if(userManagerMapper.disableUser(userEntity.getId(),userEntity.getIsForbidden()) > 0){
                if(inserOperatingRecord(request,userManagerMapper.getUserByUserId(userEntity.getId()).getUserTypeId()
                        ,userEntity.getIsForbidden() == 1?Constants.OPERATING_DISABLE_SERVICE_MSG:Constants.OPERATING_RELIEVE_DISABLE_SERVICE_MSG)){
                    Integer userId = (Integer) request.getSession().getAttribute("userId");
                    if(userEntity.getId().equals(userId)){
                        request.setAttribute("isForbidden",1);
                        result = Integer.valueOf(-1);
                    }else {
                        result = Integer.valueOf(1);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean updatePassword(HttpServletRequest request, String password) {
        boolean result = false;
        int userId = GetSessionUtil.GetSessionUserId(request);
        if(userId > 0 && (null != password && !password.equals(""))){
            if(userManagerMapper.updateUserPassword(password,userId) > 0){
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean logoutUser(HttpServletRequest request, Integer identifying) {
        boolean result = false;
        int userId = GetSessionUtil.GetSessionUserId(request);
        if(userId > 0){
            if(userManagerMapper.logoutUser(userId,identifying) > 0){
                result = true;
            }
        }
        return result;
    }

    @Override
    public void updateAccessTokenByUserName(String token,String refreshToken,String loginName) {
            userManagerMapper.updateAccessTokenByUserName(token,refreshToken,loginName);
    }

    /**
     *
     * @param request
     * @return
     */
    private boolean inserOperatingRecord(HttpServletRequest request, Integer userTypeId, String msg){
        // 获取登录账号信息
        int userId = GetSessionUtil.GetSessionUserId(request);
        UserEntity userEntity1 = userManagerMapper.getUserByUserId(userId);
        // 添加操作日志
        OperatingRecordEntity operatingRecordEntity = operatingRecordEntity(userEntity1.getId(),userTypeId,msg);
        return operatingRecordService.insertOperatingRecord(operatingRecordEntity);
    }
    /**
     * 对操作模块赋值
     * @return
     */
    private OperatingRecordEntity operatingRecordEntity(Integer userId, Integer userTypeId, String msg){
        OperatingRecordEntity operatingRecordEntity = null;
        if(null != userTypeId && userTypeId > 0){
            operatingRecordEntity = new OperatingRecordEntity();
            switch (userTypeId){
                case Constants.USER_SUPER_ADMINISTRATOR:
                    operatingRecordEntity.setOperatingModelTypeId(Constants.OPERATING_ACCOUNT_MODEL);
                    break;
                case Constants.PRE_SALES_SERVICE_MANAGE:
                case Constants.PRE_SALES_SERVICE:
                    operatingRecordEntity.setOperatingModelTypeId(Constants.OPERATING_PRE_MODEL);
                    break;
                case Constants.AFTER_SALE_SERVICE_MANAGE:
                case Constants.AFTER_SALE_SERVICE:
                    operatingRecordEntity.setOperatingModelTypeId(Constants.OPERATING_AFTER_MODEL);
                    break;
                case Constants.ACCOUNT_NUMBER_SERVICE_MANAGE:
                case Constants.ACCOUNT_NUMBER_SERVICE:
                    operatingRecordEntity.setOperatingModelTypeId(Constants.OPERATING_ACCOUNT_MODEL);
                    break;
            }
            if(userId != null && userId > 0){
                operatingRecordEntity.setCreateTime(new Timestamp(new Date().getTime()));
                operatingRecordEntity.setOperatingIntroduce(msg);
                operatingRecordEntity.setProviderId(userId);
            }
        }
        return operatingRecordEntity;
    }
    /**
     * 对用户信息做相应的处理
     * @param userEntity 需要处理的用户信息
     * @return 处理后的用户信息
     */
    private UserEntity disposeUser(UserEntity userEntity){
        // 如果密码不为空对密码进行加密
        if(null != userEntity.getLoginPassword()){
            try {
                userEntity.setLoginPassword(SHAUtil.shaEncode(userEntity.getLoginPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 如果用户类型正常 为用户分配用户组及相关信息
        if((null != userEntity.getUserTypeId()) && (userEntity.getUserTypeId() > 0)){
            switch (userEntity.getUserTypeId()){
                case Constants.USER_SUPER_ADMINISTRATOR:
                    userEntity.setUserGroup(Constants.USER_GROUP_SUPER_ADMINISTRATOR);
                    userEntity.setAdministratorOrUser(Constants.ADMIN_FLAG);
                    break;
                case Constants.PRE_SALES_SERVICE_MANAGE:
                case Constants.AFTER_SALE_SERVICE_MANAGE:
                case Constants.ACCOUNT_NUMBER_SERVICE_MANAGE:
                    userEntity.setUserGroup(Constants.USER_GROUP_ADMINISTRATOR);
                    userEntity.setAdministratorOrUser(Constants.ADMIN_FLAG);
                    break;
                case Constants.PRE_SALES_SERVICE:
                case Constants.AFTER_SALE_SERVICE:
                case Constants.ACCOUNT_NUMBER_SERVICE:
                    userEntity.setUserGroup(Constants.USER_GROUP_OPERATOR);
                    userEntity.setAdministratorOrUser(Constants.USER_FLAG);
                    break;
            }
        }
        // 为用户赋值初始状态为离线
        userEntity.setUserStatusId(2);
        // 赋值创建时间
        userEntity.setCreateTime(new Timestamp(new Date().getTime()));
        // 启用改账号
        userEntity.setIsForbidden(0);
        return userEntity;
    }
    /**
     * 整合用户数据和用户类型数据
     * @param userEntityList 用户数据
     * @param userTypeEntityList 用户类型数据
     * @return 整合后的数据
     */
    private List<UserDTO> convertUser(List<UserEntity> userEntityList, List<UserTypeEntity> userTypeEntityList){
        List<UserDTO> userDTOList = null;
        if(null != userEntityList && null != userTypeEntityList){
            boolean condition = false;
            userDTOList = new ArrayList<>();
            for(int i = 0;i < userEntityList.size();i++){
                // 获取单个用户信息
                UserEntity userEntity = userEntityList.get(i);
                for(int j = 0;j < userTypeEntityList.size();j++){
                    // 获取单个用户类型信息
                    UserTypeEntity userTypeEntity = userTypeEntityList.get(j);
                    if(userEntity.getUserTypeId().equals(userTypeEntity.getId())){
                        // 创建整合用户信息对象
                        UserDTO userDTO = new UserDTO();
                        // 赋值用户序号
                        userDTO.setId(userEntity.getId());
                        userDTO.setShowUserId(Integer.valueOf(i + 1));
                        // 赋值用户名称
                        userDTO.setUserName(userEntity.getUserName());
                        // 赋值用户登陆名称
                        userDTO.setLoginName(userEntity.getLoginName());
                        // 赋值用户座席号
                        userDTO.setSeatNumber(null == userEntity.getUserSeatNumber() ? "无" : userEntity.getUserSeatNumber());
                        // 赋值用户邮箱
                        if(null == userDTO.getUserEmail() || "".equals(userDTO.getUserEmail())){
                            userDTO.setUserEmail("无");
                        }else {
                            userDTO.setUserEmail(userDTO.getUserEmail());
                        }
                        // 赋值用户类型
                        userDTO.setUserType(userTypeEntity.getUserTypeName());
                        // 赋值用户创建时间
                        String createTime = DateUtil.dateToDateString(userEntity.getCreateTime(),DateUtil.DATE_HOUR_FORMAT);
                        userDTO.setCreateTime(createTime);
                        userDTO.setIsForbidden(userEntity.getIsForbidden());
                        userDTOList.add(userDTO);
                        break;
                    }
                }
            }
        }
        return userDTOList;
    }

    /**
     * 获取查询条件的Map类型
     * @param userCondition 查询条件
     * @return 查询条件Map类型
     */
    private String getUserConditionMap(UserEntity userCondition, Integer loginTypeId){
        String condition = "";
        if(null != userCondition.getUserName()){
            condition = " AND user_name LIKE \'%"+ userCondition.getUserName() +"%\'";
        }
        if(null != userCondition.getLoginName()){
            condition = condition + " AND login_name LIKE \'%"+ userCondition.getLoginName() +"%\'";
        }
        if(null != userCondition.getUserSeatNumber()){
            condition = condition + " AND user_seat_number LIKE \'%"+ userCondition.getUserSeatNumber() +"%\'";
        }
        if(null != loginTypeId && loginTypeId > 0){
            if(loginTypeId == 1){
                if(null != userCondition.getUserTypeId() && userCondition.getUserTypeId() > 0){
                    condition = condition + " AND user_type_id = "+ userCondition.getUserTypeId();
                }
            }else if(loginTypeId > 1 && loginTypeId < 5){
                condition = condition + " AND user_type_id = "+ (loginTypeId.intValue() + 3);
            }
        }
        return condition;
    }
}
