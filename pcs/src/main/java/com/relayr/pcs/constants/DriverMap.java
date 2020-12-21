package com.relayr.pcs.constants;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author asharma2
 *
 */
public class DriverMap{

	 /**
	 * contains mapping between data source and driver required
	 */
	public static Map<String, String> driverMap = Stream.of(new String[][] {
		  { "postgresql", "org.postgresql.Driver" }, 
		}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	

}
