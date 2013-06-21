package com.zuni.serviceprovider.exception;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public class ErrorMessage {

	private String errorCode;
	
	private String defaultMessage;
	
	public ErrorMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	

	public ErrorMessage(String errorCode, String defaultMessage) {
		this(defaultMessage);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}


	@Override
	public String toString() {
		return "errorCode=" + errorCode + ": defaultMessage=" + defaultMessage ;
	}
	
}
