package com.relayr.pcs.exception;

import lombok.Getter;

/**
 * @author asharma2
 *
 */
@Getter
public class CustomException extends Exception {

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

	public CustomException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	private String errorCode;

	private String errorMessage;

	private Throwable throwable;

}
