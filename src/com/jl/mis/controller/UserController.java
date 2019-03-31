package com.jl.mis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.dto.MenuDTO;
import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.model.entity.UserTypeEntity;
import com.jl.mis.service.UserJurisdictionRelationService;
import com.jl.mis.service.UserManagerService;
import com.jl.mis.service.UserTypeService;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.PageTools;
import com.jl.mis.utils.GetSessionUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 用户操作对外接口
 * @date 2018-5-21
 */
@Controller
@RequestMapping("callback/userManager")
public class UserController {
    @Autowired
    private UserManagerService userService;
    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    private UserJurisdictionRelationService userJurisdictionRelationService;

    /**
     * 访问示例: localhost:8080/listUserType
     * 获取所有用户类型
     * @return 用户类型列表
     */
    @ResponseBody
    @RequestMapping(value = "/listUserType",method = RequestMethod.POST)
    public JSONObject listUserType(){
        JSONObject jsonObject = new JSONObject();
        List<UserTypeEntity> listUserTypeAll = userTypeService.listUserTypeAll();
        jsonObject.put("userTypeAll",listUserTypeAll);
        return jsonObject;
    }

    /**
     * 需要用户登录
     * 访问示例:localhost:8080/listUserTypeByLoginType
     * 根据登录人类型 id 查询用户类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listUserTypeByLoginType",method = RequestMethod.POST)
    public JSONObject listUserTypeByLoginType(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        List<UserTypeEntity> userTypeEntityList = userTypeService.listUserTypeByUserTypeId(request);
        jsonObject.put("userType",userTypeEntityList);
        return jsonObject;
    }
    /**
     * 访问示例:localhost:8080/listUserAll
     * 获取所有用户
     * @return 用户列表
     */
    @ResponseBody
    @RequestMapping(value = "/listUserAll",method = RequestMethod.POST)
    public DataTables listUserAll(HttpServletRequest request){
        DataTables pageTools = userService.listUserAll(request);
        return pageTools;
    }


    /**
     * 需要用登录
     * 访问示例:localhost:8080/listUser?userName=xiao
     * 根据条件查询用户
     *                 -1 首页    -2 末页
     *                  1 上一页   2 下一页
     * @param defined 自定义跳转页面
     * @param userEntity 查询条件
     * @param request
     * @return 用户列表
     */
    @ResponseBody
    @RequestMapping(value = "/listUser",method = RequestMethod.POST)
    public DataTables listUser(Integer defined,UserEntity userEntity,HttpServletRequest request){
        DataTables dataTables = userService.listUserByCondition(defined,userEntity,request);
        return dataTables;
    }
    /**
     * 需要用户登录
     * 访问示例:localhost:8080/listUserMenu
     * 获取用户权限
     * @param request
     * @return 用户拥有的权限
     */
    @ResponseBody
    @RequestMapping(value = "/listUserMenu",method = RequestMethod.POST)
    public JSONObject listUserMenu(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Integer userType = GetSessionUtil.GetSessionUserTypeId(request);
        List<MenuDTO> menuDetailEntity = userJurisdictionRelationService.listMenuByUserTypeId(userType);
        jsonObject.put("data",menuDetailEntity);
        return jsonObject;
    }

    /**
     * 根据用户 id 查找用户信息
     * @param userId 用户 id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserByUserId",method = RequestMethod.POST)
    public JSONObject getUserByUserId(Integer userId){
        JSONObject jsonObject = new JSONObject();
        UserEntity userEntity = userService.selectById(userId);
        jsonObject.put("user",userEntity);
        return jsonObject;
    }
    /**
     * 访问示例: localhost:8080/judgeLoginName?userName=xiaohui
     * 判断登录名称是否被占用
     * @param userName 登录名称
     * @return 返回信息
     */
    @ResponseBody
    @RequestMapping(value = "/judgeLoginName",method = RequestMethod.POST)
    public JSONObject judgeLoginName(String userName){
        JSONObject jsonObject = new JSONObject();
        if(null != userService.selectUserByLoginName(userName)){
            jsonObject.put("success",true);
            jsonObject.put("msg","登录名称已占有");
        }else {
            jsonObject.put("success",false);
        }
        return jsonObject;
    }

    /**
     * 访问示例: localhost:8080/insertUser
     *      表单提交参数
     *          userName    用户名称
     *          loginName   登录名称
     *          loginPassword 登录密码
     *          userEmail   用户邮箱
     *          userTypeId  用户类型(  数字 )
     *          userSeatNumber  坐席号码
     * 添加用户
     * @param userEntity 用户信息
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/insertUser",method = RequestMethod.POST)
    public JSONObject insertUser(HttpServletRequest request,UserEntity userEntity){
        JSONObject jsonObject = new JSONObject();
        if (userService.insertUser(request,userEntity)){
            jsonObject.put("success",true);
            jsonObject.put("msg",Constants.UPDATE_SUCCESS_MSG);
        }else {
            jsonObject.put("success",false);
            jsonObject.put("msg",Constants.UPDATE_FAILURE_MSG);
        }
        return jsonObject;
    }

    /**
     * 访问示例: localhost:8080/updateUser
     *          表单提交参数
     *              userName    用户名称
     *              loginName   登录名称
     *              userEmail   用户邮箱
     *              userTypeId  用户类型(  数字 )
     *              userSeatNumber  坐席号码
     * 修改用户
     * @param request
     * @param userEntity 用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public JSONObject updateUser(HttpServletRequest request,UserEntity userEntity){
        JSONObject jsonObject = new JSONObject();
        if(userService.updateUser(request,userEntity)){
            jsonObject.put("success",true);
        }
        return jsonObject;
    }
    /**
     * 访问示例: localhost:8080/disableUser?id=2
     * 禁用用户
     * @param userEntity 用户信息
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/disableUser",method = RequestMethod.POST)
    public JSONObject disableUser(HttpServletRequest request,UserEntity userEntity){
        JSONObject jsonObject = new JSONObject();
        Integer code = userService.disableUser(request,userEntity);
        if(code != 0){
            jsonObject.put("success",true);
            jsonObject.put("code",code);
            jsonObject.put("msg","禁用成功");
        }else {
            jsonObject.put("success",false);
            jsonObject.put("msg","禁用失败");
        }
        return jsonObject;
    }

    /**
     * 访问示例: localhost:8080/getUserPassword
     * 获取随机生成的十二位密码
     *      密码格式    6 位字母 + 6 位数字（asdfghj125346）
     * @return 生成的密码
     */
    @ResponseBody
    @RequestMapping(value = "/getUserPassword",method = RequestMethod.POST)
    public JSONObject getUserPassword(){
        JSONObject jsonObject = new JSONObject();
        try {
            String password = userService.getPassword();
            jsonObject.put("success",true);
            jsonObject.put("msg",password);
        }catch (Exception e){
            jsonObject.put("success",false);
        }
        return jsonObject;
    }
}
