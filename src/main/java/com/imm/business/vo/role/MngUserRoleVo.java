package com.imm.business.vo.role;

import com.imm.business.vo.BaseVo;

public class MngUserRoleVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private Long mngUser;
	
	private Long roleId;

	public Long getMngUser() {
		return mngUser;
	}

	public void setMngUser(Long mngUser) {
		this.mngUser = mngUser;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public MngUserRoleVo(){}
	
	public MngUserRoleVo(Long mngUser, Long roleId) {
		super();
		this.mngUser = mngUser;
		this.roleId = roleId;
	}
	
	
}
