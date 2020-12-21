package com.relayr.pcs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @author asharma2
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {


	/**
	 * Exception Handler for CustomException
	 * 
	 * @param customException
	 * @return responseEntity
	 */
	@ExceptionHandler(CustomException.class) public
	ResponseEntity<CommonResponse> customExceptionHandler(CustomException customException){ 
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setErrorCode(customException.getErrorCode());
		commonResponse.setMessage(customException.getErrorMessage());
		return new ResponseEntity<>(commonResponse,HttpStatus.INTERNAL_SERVER_ERROR); 
	}



}