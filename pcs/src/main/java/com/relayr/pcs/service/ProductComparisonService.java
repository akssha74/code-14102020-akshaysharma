package com.relayr.pcs.service;

import java.util.List;

import com.relayr.pcs.bean.ProductBean;

/**
 * @author asharma2
 *
 */
public interface ProductComparisonService {

	/**
	 * @param productBean
	 * @return
	 */
	List<ProductBean> getProducts(ProductBean productBean); 
	
	/**
	 * @param productBeans
	 * @return
	 */
	List<ProductBean> saveProducts(List<ProductBean> productBeans); 
}
