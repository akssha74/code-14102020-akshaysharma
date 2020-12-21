package com.relayr.pcs.constants;

/**
 * @author asharma2
 *
 */
public enum ErrorMessages {
	
	APP01("APP01","This Database is not yet supported"),
	APP02("APP02","Unable to Fetch data from database"),
	APP03("APP03","File is empty"),
	APP04("APP04","This CSV file is not Valid"),
	APP05("APP05","Unable TO fetch data from Given Endpoint"),
	APP06("APP06","Json is not Valid"),
	APP07("APP07","This File Type is not yet supported");
	String errorCode;
	
	String errorMessage;

	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	private ErrorMessages(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String message() {
		return errorMessage;
	}
	
	public String code() {
		return errorCode;
	}
}
