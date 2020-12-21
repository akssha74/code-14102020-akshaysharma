package com.relayr.pcs.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author asharma2
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductBean implements Serializable {

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
