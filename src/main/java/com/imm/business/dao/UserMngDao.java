package com.imm.business.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.jdbc.AbstractJdbcDao;
import com.imm.common.jdbc.CommonDao;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.common.util.Page;


@Component("userMngDao")
public class UserMngDao extends AbstractJdbcDao{
	
	final static Log log = LogFactory.getLogger(UserMngDao.class);

	@Autowired
	private CommonDao commonDao;
	
	//get the statement of management account
	public Page getUsers(int pageNum, int pageSize, String searchText) {
		String sql = "select mng.ID, mng.LOGIN_ID, mng.NAME, mng.EMAIL, mng.MOBILE, mng.PASSWORD, mng.CREATE_TIME, mng.`STATUS`"; 
		sql += " from mng_user mng";
		
		final List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotEmpty(searchText)) {
			sql += " where (mng.login_id like ? or mng.name like ? or mng.email like ?)"; //search the management account by login id, name, and email
			searchText = "%" + searchText + "%";
			paramList.add(searchText);
			paramList.add(searchText);
			paramList.add(searchText);
		}

		sql += " order by mng.CREATE_TIME desc";

		return commonDao.getPage(sql, pageNum, pageSize, paramList, UserMngVo.class);
	}
	
	public UserMngVo getUserById(Long id) {
		String sql = "select mng.ID, mng.LOGIN_ID, mng.NAME, mng.EMAIL, mng.MOBILE, mng.PASSWORD, mng.CREATE_TIME, mng.`STATUS`"; 
		sql += " from mng_user mng where mng.ID = ?";
		
		UserMngVo vo = (UserMngVo)super.findObject(sql, id, UserMngVo.class);
		
		return vo;
	}
	
	public UserMngVo getUserByLoginId(String loginId){
		
		String sql = "select mng.ID, mng.LOGIN_ID, mng.NAME, mng.EMAIL, mng.MOBILE, mng.PASSWORD, mng.CREATE_TIME, mng.`STATUS`"; 
		sql += " from mng_user mng where mng.LOGIN_ID = ?";

		Object[] params = {loginId};
		List<UserMngVo> lists = super.findList(sql, Arrays.asList(params), UserMngVo.class);
		if(null!=lists && lists.size()>0){
			return lists.get(0);
		}
		return null;
	}
	
	public boolean addOrEditUser(UserMngVo userMngVo){
		boolean isNew = true;
		if (userMngVo.getId() != null){
			isNew = false;
		}
		String sql = null;
		if (isNew){
			sql = "insert into mng_user (login_id, name, email, mobile, password";
			sql += ", create_time, status) values (?, ?, ?, ?, ?, ?, ?)";			
		} else {
			sql = "update mng_user set LOGIN_ID=?,NAME=?,EMAIL=?,MOBILE=?,PASSWORD=?,CREATE_TIME=?,STATUS=? where ID=?";
		}
		Object[] params = {userMngVo.getLoginId(), userMngVo.getName(), userMngVo.getEmail(), 
				userMngVo.getMobile(), userMngVo.getPassword(), userMngVo.getCreateTime(), userMngVo.getStatus()};

		final List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(Arrays.asList(params));
		
		if (isNew){
			long id = super.saveObject(sql, Arrays.asList(params));
			userMngVo.setId(id);
		} else {
			paramList.add(userMngVo.getId());
			super.doUpdate(sql, paramList);

		}
		
		return true;
	}

	//delete
	public boolean deleteUser(Long id){
		String sql = "update mng_user set STATUS = 3 where ID = ?";

		Object[] params = {id};
		super.doUpdate(sql, Arrays.asList(params));
		return true;
	}

public List<UserMngVo> getAllUser(){
	String sql = "select mng.ID, mng.LOGIN_ID, mng.NAME, mng.EMAIL, mng.MOBILE, mng.PASSWORD, mng.CREATE_TIME, mng.`STATUS`"; 
	sql += " from mng_user mng ";
	Object[] params = { };
	List<UserMngVo> lists = super.findList(sql, Arrays.asList(params), UserMngVo.class);
	return lists ;
	
}

}
 