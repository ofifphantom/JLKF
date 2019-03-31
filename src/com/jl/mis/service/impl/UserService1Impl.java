package com.jl.mis.service.impl;

import com.jl.mis.mapper.UserMapper;
import com.jl.mis.model.User;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.DataTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:55:13
 * @Description TODO
 */
@Service
public class UserService1Impl implements UserService1 {

	@Autowired
	private UserMapper userMapper;
	
	//原start
	@Override
	public int saveEntity(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User t) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.insert(t);
	}

	//(APP & PC通用)
	@Override
	public int insertSelective(User t) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(t);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	////(APP & PC通用)
	@Override
	public int updateByPrimaryKeySelective(User t) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(User t) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(t);
	}
	
	@Override
	public User findUserByLoginName(String loginName) {
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("loginName", loginName);
		return userMapper.selectAdminByLoginName(map);
	}
	
	@Override
	public DataTables getAdministratorMsg(DataTables dataTables,String name,String telphoneNo,String operatorTime) {
		String[] columns = { "identifier", "name", "phone","user_group","operator_identifier","create_time","id" };
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		//保存搜索词
		params.put("name", name);
		params.put("telphoneNo", telphoneNo);
		params.put("operatorTime", operatorTime);
		dataTables.setiTotalDisplayRecords(userMapper
				.iTotalDisplayRecords(params));// 搜索结果总行数
		dataTables.setiTotalRecords(userMapper.iTotalRecords(params));// 所有记录总行数
		dataTables.setData(userMapper.selectForSearch(params));// 返回的结果集
		return dataTables;
		
	}
	
	
	@Override
	public User selectAdminMsgById(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectAdminMsgById(Integer.parseInt(id));
	}

	@Override
	public User selectByPrimaryKeyAndLoginName(User u) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKeyAndLoginName(u);
	}

	
   
}