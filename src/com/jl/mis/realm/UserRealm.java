package com.jl.mis.realm;

import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.service.UserManagerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserManagerService userService;
	
	/**
	 * 用户权限认证
	 * */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//authorizationInfo.setStringPermissions(userService.findPermissions(user.getRoleId()));
		return authorizationInfo;
	}

	/**
	 * 用户登录信息验证
	 * */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String loginName = token.getPrincipal().toString();
		UserEntity userEntity = userService.selectUserByLoginName(loginName);
		if(null == userEntity){
			throw new UnknownAccountException();//没找到帐号
		}
		if(null != userEntity.getIsForbidden() && userEntity.getIsForbidden() == 1){
			throw new DisabledAccountException();
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(loginName,userEntity.getLoginPassword(),getName());
		return authenticationInfo;
	}

}
