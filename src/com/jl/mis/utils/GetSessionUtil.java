package com.jl.mis.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @since2017年1月20日10:13:47
 * @author wk
 * 公共的Session获取类
 */
public class GetSessionUtil {
	
	/**
	 * 获取当前用户ID
	 * @param request
	 * @return 用户ID
	 */
	public static int GetSessionUserId(HttpServletRequest request){
		int sessionUserId = (int) request.getSession().getAttribute("userId");
		return sessionUserId;
	}
	
	/**
	 * 获取当前用户所属公司ID
	 * @param request
	 * @return 公司ID
	 */
	public static int GetSessionUserCompanyId(HttpServletRequest request){
		int sessionUserId = 0;
		if(null != request.getSession().getAttribute("userId")){
			sessionUserId = (int) request.getSession().getAttribute("userId");
		}
		return sessionUserId;
	}
	
	/**
	 * 获取当前用户角色ID
	 * @param request
	 * @return 角色ID
	 */
	public static int GetSessionUserRoleId(HttpServletRequest request){
		int sessionUserId = (int) request.getSession().getAttribute("roleId");
		return sessionUserId;
	}
	
	/**
	 * 获取当前用户用户名
	 * @param request
	 * @return 用户用户名
	 */
	public static String GetSessionUserName(HttpServletRequest request){
		String sessionUserId = (String) request.getSession().getAttribute("userName");
		return sessionUserId;
	}
	
	/**
	 * 获取当前用户登录名
	 * @param request
	 * @return 用户登录名
	 */
	public static String GetSessionUserLoginNameId(HttpServletRequest request){
		String sessionUserId = (String) request.getSession().getAttribute("loginName");
		return sessionUserId;
	}
	
	/**
	 * 获取当前登录人的编号
	 * @param request
	 * @return 登录人编号
	 */
	public static String GetSessionUserIdentifier(HttpServletRequest request){
		String sessionUserIdentifier = (String) request.getSession().getAttribute("identifier");
		return sessionUserIdentifier;
	}
	public static int GetSessionUserTypeId(HttpServletRequest request){
		int sessionUserTypeId = (int) request.getSession().getAttribute("userType");
		return sessionUserTypeId;
	}

	/**
	 * 获取客服分页帮助类
	 * @param request
	 * @return 客服分页帮助类
	 */
	public static PageTools GetSessionUserDataTables(HttpServletRequest request){
		PageTools pageTools = null;
		if(null != request.getSession().getAttribute("UserServicePageTools")){
			pageTools = (PageTools) request.getSession().getAttribute("UserServicePageTools");
		}else {
			pageTools = new PageTools();
			request.getSession().setAttribute("UserServicePageTools",pageTools);
		}
		return pageTools;
	}

	/**
	 * 获取咨询列表分页帮助类
	 * @param request
	 * @return 咨询列表帮助类
	 */
	public static PageTools GetSessionClientDataTables(HttpServletRequest request){
		PageTools pageTools = null;
		if(null != request.getSession().getAttribute("clientPageTools")){
			pageTools = (PageTools) request.getSession().getAttribute("clientPageTools");
		}else {
			pageTools = new PageTools();
			request.getSession().setAttribute("clientPageTools",pageTools);
		}
		return pageTools;
	}
}
