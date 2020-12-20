package com.relayr.pcs.constants;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DriverMap {

	 public static Map<String, String> driverMap = Stream.of(new String[][] {
		  { "postgresql", "org.postgresql.Driver" }, 
		}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	

}
