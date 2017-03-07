package com.imm.business.service.role;

import java.util.List;

import com.imm.business.vo.role.MngUserRoleVo;

public interface MngUserRoleService {

	/**
	 * 根据用户id查询当前用户的所有权限集合
	 * @param userId 用户id
	 * @return 权限集合
	 */
	public List<MngUserRoleVo> getMngUserRoleByUserId(Long userId);
	
	
}
