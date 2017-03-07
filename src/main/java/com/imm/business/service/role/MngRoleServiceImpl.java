package com.imm.business.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imm.business.dao.role.MngRoleDao;
import com.imm.business.vo.role.MngRoleVo;

@Service("mngRoleService")
@Transactional(readOnly = true)
public class MngRoleServiceImpl implements MngRoleService {

	@Autowired
	private MngRoleDao mngRoleDao;

	public MngRoleVo getMngRoleById(Long id) {
		MngRoleVo mngRoleVo = mngRoleDao.getMngRoleById(id);
		return mngRoleVo;
	}
	
}
