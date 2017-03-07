package com.imm.business.vo.mng;

import java.util.Date;
import java.util.List;

import com.imm.business.vo.BaseVo;

public class UserMngVo extends BaseVo{
	
	private static final long serialVersionUID = -1L;
	
	private String loginId;       //登录ID
	private String name;          //真实姓名
	private String email;         //电子邮件地址
	private String mobile;        //手机号码
	private String password;      //登录密码
	private Date createTime;       //注册时间
	private Short status;         //用户状态
	/** 用户角色（多个）*/
	private List<Integer> roles;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public List<Integer> getRoles() {
		return roles;
	}
	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

}
