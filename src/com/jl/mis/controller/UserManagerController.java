package com.jl.mis.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.Menu;
import com.jl.mis.model.Permission;
import com.jl.mis.model.User;
import com.jl.mis.service.MenuService;
import com.jl.mis.service.PermissionService;
import com.jl.mis.service.UserManagerService;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月11日 下午3:36:00
 * @Description 用户管理Controller
 *
 */
@Controller
@RequestMapping("backgroundManagement/background/userManager/")
public class UserManagerController extends BaseController {
	@Autowired
	private UserService1 userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserManagerService userManagerService;
	/**
	 * 进入用户（管理员）管理页面 page 1:用户（管理员）页面 2：修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, int page) {
		if (!checkSession(request)) {
			return "redirect:/login";
		}
		if (page == 1) {
			return "进入用户（管理员）页面";
		} else if (page == 2) {
			return "进入用户页面";
		} else {
			return "进入修改密码页面";
		}
	}

	/**
	 * 从数据库中获取管理员信息
	 * 
	 * @param request
	 * @param name
	 *            页面搜索--名称
	 * @param telphoneNo
	 *            页面搜索--电话号码
	 */
	@ResponseBody
	@RequestMapping(value = "getAdministratorMsg")
	public DataTables getAdministratorMsg(HttpServletRequest request, String name, String telphoneNo, String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);
		return userService.getAdministratorMsg(dataTables, name, telphoneNo,operatorTime);
	}

	/**
	 * 新增前，查询登录名是否存在
	 * 
	 * @param request
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "selectAdminByLoginName")
	public JSONObject selectAdminByLoginName(HttpServletRequest request, String loginName) throws Exception {
		JSONObject rmsg = new JSONObject();
		User u = userService.findUserByLoginName(loginName);
		if (u != null) {
			// 往前台返回结果集
			rmsg.put("success", false);
			rmsg.put("msg", "该登录名已被占用!");
			return rmsg;
		}
		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 新增管理员信息
	 * 
	 * @param request
	 * @param user
	 *            前台输入的管理员信息
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertAdminMsg")
	public JSONObject insertAdminMsg(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		User user = new User();
		// 从前台获取数据
		user.setLoginName(request.getParameter("loginName"));
		user.setName(request.getParameter("name"));
		user.setPhone(request.getParameter("phone"));
		user.setPassword(request.getParameter("password"));
		user.setUserGroup(Integer.parseInt(request.getParameter("userGroup")));
		// 从前台获取选择的权限
		String[] permissions = request.getParameter("resIds").split(",");
		// 自动生成编号
		user.setIdentifier(SundryCodeUtil.getPosCode(Constants.CODE_ADMIN));
		// 添加管理员标志
		user.setAdministratorOrUser(Constants.ADMIN_FLAG);
		// 默认设置新添的管理员账号为有效1：有效，0：无效
		user.setIsEffective(1);
		// 给密码加密
		user.setPassword(SHAUtil.shaEncode(user.getPassword()));
		// 添加操作时间
		Date date = new Date();
		user.setCreateTime(date);
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		user.setOperatorIdentifier(userIdentifier);
		int result = userService.insertSelective(user);
		// 获取刚才存入数据的那条数据的主键
		int id = user.getId();
		if (result > 0) {
			// 往权限表中批添加该管理员的权限
			permissionService.insertBatch(permissions, id, userIdentifier, date);
			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.SAVE_SUCCESS_MSG);
			return rmsg;
		}

		rmsg.put("success", false);
		rmsg.put("msg", Constants.SAVE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 点击编辑时，根据主键查询管理员信息以及所拥有的权限
	 * 
	 * @param request
	 * @param id
	 *            管理员主键id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectAdminMsgById")
	public User selectAdminMsgById(HttpServletRequest request, String id) {

		return userService.selectAdminMsgById(id);

	}
	
	/**
	 * 编辑前，查询登录名是否存在
	 * 
	 * @param request
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "selectAdminByLoginNameAndId")
	public JSONObject selectAdminByLoginNameAndId(HttpServletRequest request, User user) throws Exception {
		JSONObject rmsg = new JSONObject();
		User u = userService.selectByPrimaryKeyAndLoginName(user);
		if (u != null) {
			// 往前台返回结果集
			rmsg.put("success", false);
			rmsg.put("msg", "该登录名已被占用!");
			return rmsg;
		}
		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 编辑管理员信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateAdminMsgById")
	public JSONObject updateAdminMsgById(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		User user = new User();
		// 从前台获取数据
		user.setId(Integer.parseInt(request.getParameter("primaryKey")));
		user.setName(request.getParameter("name"));
		user.setPhone(request.getParameter("phone"));
		user.setUserGroup(Integer.parseInt(request.getParameter("userGroup")));
		// 从前台获取选择的权限
		String[] permissions = request.getParameter("resIds").split(",");
		// 从前台获取用户编号
		String identifier = request.getParameter("updateIdentifier");
		// 添加操作时间
		Date date = new Date();
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);

		int result = userService.updateByPrimaryKeySelective(user);

		if (result > 0) {
			// 修改时先删除权限表中属于该管理员的所有权限，重新添加
			if (permissionService.deleteByUserId(user.getId()) >= 0) {
				// 往权限表中批添加该管理员的权限
				permissionService.insertBatch(permissions, user.getId(), userIdentifier, date);
			}

			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}

		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 删除管理员
	 * 
	 * @param request
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteByPrimaryKey", method = RequestMethod.POST)
	public JSONObject deleteByPrimaryKey(HttpServletRequest request, User user) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 根据主键删除管理员
		int result = userService.deleteByPrimaryKey(user.getId());
		// 操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);

		if (result > 0) {
			// 根据管理员id删除权限表中对应的权限
			permissionService.deleteByUserId(user.getId());

			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.DELE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.DELE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 管理员禁用操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserForbidden", method = RequestMethod.POST)
	public JSONObject updateUserForbidden(HttpServletRequest request, User u) throws Exception {
		JSONObject rmsg = new JSONObject();

		// 前台传来的当前需要操作的管理员处于未禁用状态，需要改成禁用状态
		if (u.getIsEffective() == 0) {
			u.setIsEffective(1);
		}
		// 前台传来的当前需要操作的管理员处于禁用状态，需要改成未禁用状态
		else {
			u.setIsEffective(0);
		}
		// 更新当前管理员的状态
		int result = userService.updateByPrimaryKeySelective(u);
		if (result > 0) {
			// 添加操作人编号，从session中获取
			String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 判断输入的原密码与登陆的密码是否一样
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "decidePwd")
	public JSONObject decidePwd(HttpServletRequest request, String password) throws Exception {
		JSONObject rmsg = new JSONObject();
		if ((request.getSession().getAttribute("password")).equals(SHAUtil.shaEncode(password))) {
			rmsg.put("success", true);
			return rmsg;
		}
		rmsg.put("success", false);
		return rmsg;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updatePwd")
	public JSONObject updatePwd(HttpServletRequest request, String password) throws Exception {
		JSONObject rmsg = new JSONObject();
		if (userManagerService.updatePassword(request,SHAUtil.shaEncode(password))) {
			rmsg.put("success", true);
			rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}

	
	
	@ResponseBody
	@RequestMapping(value = "selectMenuById")
	public JSONArray selectMenuById(HttpServletRequest request, String id) {
		HttpSession session=request.getSession();
		List<Menu> list=new ArrayList<Menu>();
		List<Menu> listall=menuService.selectByAll();
		List<Integer> parent=new ArrayList<Integer>();
		for (Permission object : userService.selectAdminMsgById(id).getPermissions()) {
			/*if(object.getMenu().getId()==38){
				object.getMenu().setUrl(object.getMenu().getUrl()+session.getAttribute("loginName"));
			}*/
			if(object.getMenu().getSort()!=null&&object.getMenu().getSort()==0){
				parent.add(object.getMenu().getId());
			}else{
				list.add(object.getMenu());
			}
			
			boolean have=false;
			for(Integer Pid:parent){
				if(Pid==object.getMenu().getParentId()){
					have=true;
				}
			}
			if(object.getMenu().getSort()==null||object.getMenu().getSort()!=0){
				if(!have){
					parent.add(object.getMenu().getParentId());
				}
			}
			
		}
		for(Menu menu:listall){
			if(menu.getId()==38){
				menu.setUrl(menu.getUrl()+session.getAttribute("loginName"));
			}
			for(Integer Pid:parent){
				if(menu.getId()==Pid){
					list.add(menu);
				}
			}
		}
		JSONArray array=changMenutoJason(list,0);
		
		changMenutoJasonall(array);
		
		return array;
	}
	
	/**
	 * 
	 * @param 用户编辑时调用
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectAllMenu")
	public JSONArray selectAllMenu(HttpServletRequest request, String id) {
		List<Menu> listall=menuService.selectByAll();
		JSONArray menuall=changMenutoJason(listall,0);
		List<Menu> list=new ArrayList<Menu>();
		if(id!=null&&!"".equals(id)){
			for (Permission object : userService.selectAdminMsgById(id).getPermissions()) {
				list.add(object.getMenu());
			}
		}
		//JSONArray menu=changMenutoJason(list,0);
		changMenutoJasonall(menuall, list);;
		return menuall;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	public JSONArray changMenutoJason(List<Menu> menus,Integer parentid){
		JSONArray array=new JSONArray();
		for (Menu menu : menus) {
			JSONObject jsonObject=new JSONObject();
			if(menu.getSort()!=null&&menu.getSort()==0){
				if(menu.getParentId()==parentid){
					jsonObject.put("id", menu.getId());
					jsonObject.put("text", menu.getName());
					jsonObject.put("state", "closed");
					jsonObject.put("children", changMenutoJason(menus,menu.getId()));
					array.add(jsonObject);
				}
			}else{
				if(menu.getParentId()==parentid){
					jsonObject.put("id", menu.getId());
					jsonObject.put("text", menu.getName());
					jsonObject.put("iconCls","icon-no");
					JSONObject o=new JSONObject();
					o.put("name", menu.getUrl());
					jsonObject.put("attributes", o);
					array.add(jsonObject);
				}
			}
			
		}
		return array;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	
	public void changMenutoJasonall(JSONArray menusall,List<Menu> menu){
		for(int i=0;i<menusall.size();i++){
			((JSONObject) menusall.get(i)).put("state", "open");
			for(int j=0;j<menu.size();j++){
				if(((JSONObject) menusall.get(i)).get("id").equals(menu.get(j).getId())){
					((JSONObject) menusall.get(i)).put("checked", true);
					break;
				}
			}
			if(((JSONObject) menusall.get(i)).get("children")!=null){
				changMenutoJasonall((JSONArray) ((JSONObject) menusall.get(i)).get("children"),menu);
			}
		}
	}
	
	public void changMenutoJasonall(JSONArray menusall){
		if(menusall.get(0)!=null){
			((JSONObject) menusall.get(0)).put("state", "open");
			if(((JSONObject) menusall.get(0)).get("children")!=null){
				changMenutoJasonall((JSONArray)((JSONObject) menusall.get(0)).get("children"));
			}
		}
	}
	
	
}
