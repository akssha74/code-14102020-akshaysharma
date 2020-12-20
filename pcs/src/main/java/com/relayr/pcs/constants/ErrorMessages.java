package com.relayr.pcs.constants;

public enum ErrorMessages {
	
	APP01("APP01","Data Source not Found");
	
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
