package com.imm.business.vo;

import java.util.Date;

public class ClientVo  extends BaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1553004318224276776L;
	 public String clientName;
	 public String personalInfo;
	 public String backgroundInfo;
	 public Date createTime ;
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getPersonalInfo() {
		return personalInfo;
	}
	public void setPersonalInfo(String personalInfo) {
		this.personalInfo = personalInfo;
	}
	public String getBackgroundInfo() {
		return backgroundInfo;
	}
	public void setBackgroundInfo(String backgroundInfo) {
		this.backgroundInfo = backgroundInfo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	 
}
