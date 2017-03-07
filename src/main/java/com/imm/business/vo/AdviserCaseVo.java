package com.imm.business.vo;

import java.util.Date;

public class AdviserCaseVo  extends BaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1553004318224276776L;
	public long caseId ;
	public long adviserId ;
	 
	public String clientName ;
	public String adviserName;
	public int status ;
	public Date createTime;
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	public long getAdviserId() {
		return adviserId;
	}
	public void setAdviserId(long adviserId) {
		this.adviserId = adviserId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getAdviserName() {
		return adviserName;
	}
	public void setAdviserName(String adviserName) {
		this.adviserName = adviserName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
