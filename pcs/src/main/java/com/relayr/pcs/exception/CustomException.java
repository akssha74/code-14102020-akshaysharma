package com.relayr.pcs.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Exception{
	
	/**
	 * Serialization Id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public CustomException() {
	}
	
	public CustomException(Throwable e) {
		super();
		this.throwable = e;	
	}
	
	public CustomException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public CustomException(String errorCode, Throwable throwable) {
		super();
		this.errorCode = errorCode;
		this.throwable = throwable;
	}
	
	public CustomException(String errorCode,String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public CustomException(String errorCode, String errorMessage, Throwable e)
	{
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.throwable = e;
	}
	
	private String errorCode;
	
	private String errorMessage;
	
	private Throwable throwable;
	
	
}
