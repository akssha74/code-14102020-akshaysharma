package com.relayr.pcs.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

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

	public CustomException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	private String errorCode;

	private String errorMessage;


}
