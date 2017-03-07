 package com.imm.business.vo;

public class LockUserResultVo {

	/** 用户是否登录成功*/
	private boolean flag ;
	
	/** 登录错误提示信息*/
	private String message ;
	
	/** 登录错误次数 */
	private int errorCount;
	
	/** 放入任意用户对象 */
	private Object object;

	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public LockUserResultVo() {
		
	}
	
	public LockUserResultVo(boolean flag, String message , int errorCount , Object object) {
		super();
		this.flag = flag;
		this.message = message;
		this.errorCount = errorCount;
		this.object = object;
	}
	
	
}
