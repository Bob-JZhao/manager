/*
 * 
 * 
 */
package com.imm.common.exception;

/**
 * database exception
 * @author bob zhao
 *
 */
public class DataAccessException extends BaseException {
	
	private static final long serialVersionUID = 67891L;

	public DataAccessException(String message, Throwable t) {
		super(message, t);
	}

	public DataAccessException(Throwable t) {
		super(t);
	}

	public DataAccessException(String message) {
		super(message);
	}
}
