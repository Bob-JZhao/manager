package com.imm.common.exception;

/**
 * baseexception
 * @author bob zhao
 *
 */
public class SystemException extends BaseException {
	
	private static final long serialVersionUID = 67891L;

	public SystemException(String message, Throwable t) {
		super(message, t);
	}

	public SystemException(Throwable t) {
		super(t);
	}

	public SystemException(String message) {
		super(message);
	}
}
