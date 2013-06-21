package com.zuni.serviceprovider.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Zuned Ahmed
 *there can be many exception which can be thrown by one buisness expection
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	protected List<ErrorMessage> errorMessage;
	protected String[] args;
	
	public BusinessException(List<ErrorMessage> errorMessage, Throwable th, String... args) {
		super(errorMessage.toString(), th);
		this.errorMessage = errorMessage;
		this.args = args;
	}
	
	public BusinessException(List<ErrorMessage> errorMessage, Throwable th) {
		this(errorMessage , th , "");
	}

	public BusinessException(ErrorMessage errorMessage, Throwable th, String... args) {
		super(errorMessage.toString(), th);
		this.errorMessage = new ArrayList<ErrorMessage>();
		this.errorMessage.add(errorMessage);
		this.args = args;
	}

	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}

	public List<ErrorMessage> getErrorMessage() {
		return this.errorMessage == null ? new ArrayList<ErrorMessage>():this.errorMessage;
	}

	public void setErrorMessage(List<ErrorMessage> errorMessage) {
		this.errorMessage = errorMessage;
	}
}
