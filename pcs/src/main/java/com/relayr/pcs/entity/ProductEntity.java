package com.relayr.pcs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
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
@Entity
@EqualsAndHashCode
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
