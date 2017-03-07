package com.imm.business.dao.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.imm.business.vo.mng.UserMngVo;
import com.imm.business.vo.role.MngUserRoleVo;
import com.imm.common.jdbc.AbstractJdbcDao;

 
@Component("mngUserRoleDao")
public class MngUserRoleDao extends AbstractJdbcDao {

	public List<MngUserRoleVo> getMngUserRoleByUserId(Long userId){
		
		List<MngUserRoleVo> roles = new ArrayList<MngUserRoleVo>();
		
		String sql = "select mur.ID ,mur.MNG_USER ,mur.ROLE_ID from mng_user_role as mur where mur.MNG_USER = ?";
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(userId);
		
		roles = super.findList(sql, params, MngUserRoleVo.class);
		
		if(null == roles || roles.size() <= 0){
			roles = null;
		}
		
		return roles;
		
	}
	
	public boolean removeMngRoles(Long id){
		String sql = "delete from mng_user_role where mng_user = ?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(id);
		super.doUpdate(sql, paramList);
		return true;
	}
	
	public boolean addMngRole(Long mngUser,Integer roleId){
		String sql = "insert into mng_user_role (mng_user,role_id) values (?,?)";
		Object[] params = {mngUser,roleId};
		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(Arrays.asList(params));
		super.saveObject(sql, paramList);		
		return true;
	}
	
}
