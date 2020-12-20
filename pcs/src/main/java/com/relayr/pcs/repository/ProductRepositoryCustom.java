package com.relayr.pcs.repository;

import java.util.List;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.entity.ProductEntity;

public interface ProductRepositoryCustom {

	List<ProductEntity> getProducts(ProductBean productBean);
}
