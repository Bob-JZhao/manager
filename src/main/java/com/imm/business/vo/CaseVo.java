 package com.imm.business.vo;

import java.util.Date;

public class CaseVo  extends BaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1553004318224276776L;
	public String name ;
	public String clientName ;
	public String visaType ;
	public String visaOfficebranch;
	public String expectedSub;
	public String password ;
	public Date createTime;
	public Date signUpTime;
	public String visaOfficeName;
	public Date criticalDate ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public String getVisaOfficebranch() {
		return visaOfficebranch;
	}
	public void setVisaOfficebranch(String visaOfficebranch) {
		this.visaOfficebranch = visaOfficebranch;
	}
	public String getExpectedSub() {
		return expectedSub;
	}
	public void setExpectedSub(String expectedSub) {
		this.expectedSub = expectedSub;
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
	public Date getSignUpTime() {
		return signUpTime;
	}
	public void setSignUpTime(Date signUpTime) {
		this.signUpTime = signUpTime;
	}
	public String getVisaOfficeName() {
		return visaOfficeName;
	}
	public void setVisaOfficeName(String visaOfficeName) {
		this.visaOfficeName = visaOfficeName;
	}
	public Date getCriticalDate() {
		return criticalDate;
	}
	public void setCriticalDate(Date criticalDate) {
		this.criticalDate = criticalDate;
	} 

}
