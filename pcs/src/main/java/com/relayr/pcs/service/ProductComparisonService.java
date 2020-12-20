package com.relayr.pcs.service;

import java.util.List;

import com.relayr.pcs.bean.ProductBean;

public interface ProductComparisonService {

	List<ProductBean> getProducts(ProductBean productBean); 
	
	List<ProductBean> saveProducts(List<ProductBean> productBeans); 
}
