package com.relayr.pcs.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author asharma2
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class CommonResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String message;

	public String errorCode;

}
