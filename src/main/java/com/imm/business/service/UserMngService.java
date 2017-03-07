package com.imm.business.service;

import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;

/**
 * 后台管理用户相关的服务接口
 * @author khe1
 *
 */

public interface UserMngService {
	
	public Page getUsers(int pageNum, int pageSize, String searchText);
	
	public UserMngVo getUserById(Long id);
	
	public boolean addorEditUser(UserMngVo mngVo);
	
	//public boolean editUser(UserMngVo mngVo);

	public boolean deleteUser(Long id);
	
	public UserMngVo getUserByLoginId(String loginId);

	boolean updatePass(UserMngVo mngVo);

}
