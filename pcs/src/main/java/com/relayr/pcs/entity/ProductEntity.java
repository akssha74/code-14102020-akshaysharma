package com.relayr.pcs.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document
@Table(name = "products")
public class ProductEntity {
	
	@Id
	@GenericGenerator(name = "unique_identifier", strategy = "com.relayr.pcs.util.UniqueIdGenerator")
	@GeneratedValue(generator = "unique_identifier")
	private String uniqueIdentifier;
	
	private String name;
	
	private String modelNumber;
	
	private String brandName;
	
	private String category;
	
	private double price;
	
	private String website;
	
}
