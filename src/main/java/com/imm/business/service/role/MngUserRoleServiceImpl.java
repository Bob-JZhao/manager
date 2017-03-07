package com.imm.business.service.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imm.business.dao.role.MngUserRoleDao;
import com.imm.business.vo.role.MngUserRoleVo;

@Service("mngUserRoleService")
@Transactional(readOnly = true)
public class MngUserRoleServiceImpl implements MngUserRoleService {

	@Autowired
	private MngUserRoleDao mngUserRoleDao;
	
	public List<MngUserRoleVo> getMngUserRoleByUserId(Long userId) {
		List<MngUserRoleVo> roles = mngUserRoleDao.getMngUserRoleByUserId(userId);
		return roles;
	}

}
