package com.relayr.pcs.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String uniqueIdentifier;
	
	private String name;
	
	private String modelNumber;
	
	private String brandName;
	
	private String category;
	
	private double lowPrice;
	
	private double highPrice;
	
	private double price;
	
	private String website;
	
	
}
