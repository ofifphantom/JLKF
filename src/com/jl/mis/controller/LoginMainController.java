package com.jl.mis.controller;

import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.service.UserManagerService;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.ResultInfo;
import com.jl.mis.utils.SHAUtil;
import com.jl.mis.utils.SundryCodeUtil;
import com.jl.mis.websocket.ResourcesFile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录页面controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class LoginMainController extends BaseController {
	@Autowired
	private ResourcesFile resourcesFile;
	@Autowired
	private UserManagerService userService;
	/**
	 * 进入登陆页面
	 * @throws Exception
	 * */
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login(HttpServletRequest request) throws Exception {
		//System.out.println(SHAUtil.shaEncode("1"));
		//用于认证数据传输
		Subject subject = SecurityUtils.getSubject();
		userService.logoutUser(request,2);
		//当你调用logout，任何现有的Session 都将会失效，而且任何身份都将会失去关联
		subject.logout();
		return "redirect:/login.jsp";
	}
	
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	@ResponseBody
	public ResultInfo login(String loginName,
                            String password,
                            String validateCode, String remenberComCodeAndLoginName,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 验证码的校验
		 * */
		String verifyCode = request.getSession().getAttribute("validateCode").toString();
		if(!validateCode.equalsIgnoreCase(verifyCode)){//验证码不通过
			info.setCode(false);
			info.setMsg("验证码错误");
		}else{
			//登陆验证
			//UsernamePasswordToken用来将从Java应用程序通过某种方式获取到的用户名和密码绑定到一起
			UsernamePasswordToken token = new UsernamePasswordToken(loginName,SHAUtil.shaEncode(password));
			//用于认证数据传输
			Subject subject = SecurityUtils.getSubject();
			try{
				subject.login(token);
				UserEntity user = userService.selectUserByLoginName(loginName);
				request.getSession().setAttribute("userId", user.getId());
				request.getSession().setAttribute("userName", user.getUserName());
				request.getSession().setAttribute("loginName", user.getLoginName());
				request.getSession().setAttribute("userType" , user.getUserTypeId());
				request.getSession().setAttribute("userGroup", SundryCodeUtil.getUserGroup(Integer.parseInt(user.getUserGroup().toString())));
				request.getSession().setAttribute("password", user.getLoginPassword());
				request.getSession().setAttribute("isForbidden",user.getIsForbidden());
				request.getSession().setAttribute("webSocketAddress",resourcesFile.getwebSocketAddress());

				//点击记住公司编号和用户名
				if(("true").equals(remenberComCodeAndLoginName)){
					Cookie cookieloginName=new Cookie("loginName", loginName);
					Cookie cookiepassword=new Cookie("password", password);
					//设置过期时间为一天
					cookieloginName.setMaxAge(60*60*24);
					cookieloginName.setPath("/");
					cookiepassword.setMaxAge(60*60*24);
					cookiepassword.setPath("/");
					//在响应头部添加cookie
					response.addCookie(cookieloginName);
					response.addCookie(cookiepassword);
				}
				else{//没有选择记住的时候，如果cookie里有用户名时则清除cookies
					Cookie cookies[]=request.getCookies();
					for(int i=0;i<cookies.length;i++){
						if("loginName".equals(cookies[i].getName())){
							if(loginName.equals(cookies[i].getValue())){
								Cookie cookieloginName=new Cookie("loginName", "");
								cookieloginName.setPath("/");
								cookieloginName.setMaxAge(0);
								response.addCookie(cookieloginName);
							}
						} else if("password".equals(cookies[i].getName())){
							if(loginName.equals(cookies[i].getValue())){
								Cookie cookieloginName=new Cookie("password", "");
								cookieloginName.setPath("/");
								cookieloginName.setMaxAge(0);
								response.addCookie(cookieloginName);
							}
						}
					}

				}
				userService.logoutUser(request,1);
				info.setCode(Constants.SUCCESS);
			}catch(IncorrectCredentialsException e){

				info.setCode(Constants.FAILURE);
				info.setMsg("用户名或密码错误!");
			}catch (UnknownAccountException e) {
				
				info.setCode(Constants.FAILURE);
				info.setMsg("用户名或密码错误!");
			}catch (DisabledAccountException e){
				info.setCode(Constants.FAILURE);
				info.setMsg("您的账号已被禁用,请联系管理员");
			}
		}
		return info;
	}
	
	/**
	 * 进入系统后台首页
	 * */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request){
		if (!checkSession(request)) {
			return "redirect:/login.jsp";
		}else{
			return "/index";
		}
	}
	
	/**
	 * 获取用户菜单
	 * *//*
	@RequestMapping(value="/getMenus",method=RequestMethod.GET)
	@ResponseBody
	public List<Tree> getMenus(HttpServletRequest request){
		int roleId = (int) request.getSession().getAttribute("roleId");
		int companyId=(int) request.getSession().getAttribute("companyId");
		if(companyId!=-1){
			return userService.getMenus(roleId);
		}
		else{
			return userService.getMenus_miniaunt(roleId);
		}
	}*/
}
