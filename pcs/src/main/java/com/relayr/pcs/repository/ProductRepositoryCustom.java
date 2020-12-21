package com.relayr.pcs.repository;

import java.util.List;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.entity.ProductEntity;

/**
 * @author asharma2
 *
 */
public interface ProductRepositoryCustom {

	/**
	 * @param productBean
	 * @return
	 */
	List<ProductEntity> getProducts(ProductBean productBean);
}
