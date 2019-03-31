package com.jl.mis.service;


import com.jl.mis.model.User;
import com.jl.mis.utils.DataTables;

/**
 * 用户Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:55:13
 * @Description TODO
 */
public interface UserService1 extends BaseService<User> {
	
	/**
	 * 根据登录名查询管理员
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName);
	
	/**
	 * 根据登录名和主键获取管理员信息
	 * @param User 用户model
	 * @return
	 */
	public User selectByPrimaryKeyAndLoginName(User u);
	
	/**
	 * 从数据库中获取管理员信息
	 * @return 返回一个用户列表对象
	 */
	public DataTables getAdministratorMsg(DataTables dataTables, String name, String telphoneNo, String operatorTime);
	
	/**
	 * 根据主键联合查询user表和permission权限表
	 * @param id user主键
	 * @return
	 */
	public User selectAdminMsgById(String id);
	

	
	
	
	
}