package com.zuni.serviceprovider.exception;

/**
 * 
 * @author Zuned Ahmed
 * on any runtime error application will throw error
 *
 */
public class BusinessRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected ErrorMessage errorMessage;
	protected String[] args;
	
	public BusinessRuntimeException(ErrorMessage errorMessage, Throwable th, String... args) {
		super(errorMessage.toString(), th);
		this.errorMessage = errorMessage;
		this.args = args;
	}
	
	public BusinessRuntimeException(ErrorMessage errorMessage, Throwable th) {
		this(errorMessage , th , "");
	}

	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
