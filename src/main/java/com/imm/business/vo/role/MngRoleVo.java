package com.imm.business.vo.role;

import com.imm.business.vo.BaseVo;

public class MngRoleVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public MngRoleVo(){}
	
	public MngRoleVo(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}
	
	
}
