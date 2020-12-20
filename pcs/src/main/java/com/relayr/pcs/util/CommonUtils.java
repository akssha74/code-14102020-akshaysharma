package com.relayr.pcs.util;

public class CommonUtils {
	public static boolean isNull(Object inputObj) {
		return null == inputObj || inputObj.toString().trim().length() == 0 || "null".equalsIgnoreCase(inputObj.toString());
	}
}
