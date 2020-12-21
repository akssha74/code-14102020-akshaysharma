package com.relayr.pcs.util;

import com.relayr.pcs.constants.DriverMap;

/**
 * @author asharma2
 *
 */
public class CommonUtils {
	/**
	 * Check if the given object is null
	 * @param inputObj
	 * @return boolean
	 */
	public static boolean isNull(Object inputObj) {
		return null == inputObj || inputObj.toString().trim().length() == 0
				|| "null".equalsIgnoreCase(inputObj.toString());
	}

	/**
	 * Fetch the required driver name from given jdbc string
	 * @param jdbc
	 * @param schema
	 * @param table
	 * @return connectionString, schema,table,driver name joined by pipe(|)
	 */
	public static String getDriver(String jdbc,String schema,String table) {
		String dbProvider = jdbc.split(":")[1];
		String result = jdbc+"|"+schema+"|"+table+"|"+DriverMap.driverMap.get(dbProvider);
		return result;
	}
	
	public static String cleanString(String input) {
		return input.trim().replace("\\n", "").replace("\\r", "");
	}
}
