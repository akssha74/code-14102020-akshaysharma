package com.relayr.pcs.util;

import com.relayr.pcs.constants.DriverMap;

public class CommonUtils {
	public static boolean isNull(Object inputObj) {
		return null == inputObj || inputObj.toString().trim().length() == 0
				|| "null".equalsIgnoreCase(inputObj.toString());
	}

	public static String getDriver(String jdbc,String schema,String table) {
		String dbProvider = jdbc.split(":")[1];
		String result = jdbc+"|"+schema+"|"+table+"|"+DriverMap.driverMap.get(dbProvider);
		return result;
	}
}
