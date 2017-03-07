package com.imm.business.service;

import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.imm.business.dao.UserMngDao;
import com.imm.business.dao.role.MngUserRoleDao;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;

@Service("userMngService")
@Transactional(readOnly = true)
public class UserMngServiceImpl implements UserMngService{
	
	final static Log log = LogFactory.getLogger(UserMngServiceImpl.class);
	
	@Autowired
	private UserMngDao userMngDao;
	
	@Autowired
	private MngUserRoleDao mngUserRoleDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Page getUsers(int pageNum, int pageSize, String searchText) {
		return userMngDao.getUsers(pageNum, pageSize, searchText);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMngVo getUserById(Long id) {
		return userMngDao.getUserById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean addorEditUser(UserMngVo mngVo) {
		boolean flag = false;
		flag = userMngDao.addOrEditUser(mngVo);
		flag = mngUserRoleDao.removeMngRoles(mngVo.getId());
		while(!mngVo.getRoles().isEmpty()){
			flag = mngUserRoleDao.addMngRole(mngVo.getId(), mngVo.getRoles().get(0));
			mngVo.getRoles().remove(0);
		}
		return flag;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updatePass(UserMngVo mngVo) {
		return userMngDao.addOrEditUser(mngVo);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteUser(Long id) {
		return userMngDao.deleteUser(id);
	}

	public UserMngVo getUserByLoginId(String loginId) {
		return userMngDao.getUserByLoginId(loginId);
	}


}
