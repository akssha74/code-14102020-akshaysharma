package com.relayr.pcs.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author asharma2
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class CommonResponse {

	public String message;

	public String errorCode;

}
