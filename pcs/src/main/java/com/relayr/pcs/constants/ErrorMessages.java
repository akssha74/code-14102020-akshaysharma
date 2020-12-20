package com.relayr.pcs.constants;

public enum ErrorMessages {
	
	APP01("APP01","This Database is not yet supported"),
	APP02("APP02","Unable to Fetch data from database"),
	APP03("APP03","File is empty"),
	APP04("APP04","This CSV file is not Valid"),
	APP05("APP05","Unble TO fetch data from Given Endpoint");
	String errorCode;
	
	String errorMessage;

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
