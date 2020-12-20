package com.relayr.pcs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.entity.ProductEntity;
import com.relayr.pcs.repository.ProductRepository;
import com.relayr.pcs.service.ProductComparisonService;
import com.relayr.pcs.util.CommonUtils;

@Service
public class ProductComparisonServiceImpl implements ProductComparisonService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<ProductBean> getProducts(ProductBean productBean) {
		return convertProductEntityToProductBean(productRepository.getProducts(productBean));
	}

	public List<ProductBean> convertProductEntityToProductBean(List<ProductEntity> productList) {
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		for (ProductEntity entity : productList) {
			ProductBean bean = new ProductBean();
			bean.setBrandName(entity.getBrandName());
			bean.setCategory(entity.getCategory());
			bean.setHighPrice(entity.getPrice());
			bean.setLowPrice(entity.getPrice());
			bean.setPrice(entity.getPrice());
			bean.setModelNumber(entity.getModelNumber());
			bean.setName(entity.getName());
			bean.setWebsite(entity.getWebsite());
			bean.setUniqueIdentifier(entity.getUniqueIdentifier());
			beanList.add(bean);
		}

		return beanList;
	}

	@Override
	public List<ProductBean> saveProducts(List<ProductBean> productBeans) {
		List<ProductEntity> pojoList = convertProductBeanToProductEntity(productBeans);
		return convertProductEntityToProductBean(productRepository.saveAll(pojoList));
	}

	private List<ProductEntity> convertProductBeanToProductEntity(List<ProductBean> productBeans) {
		List<ProductEntity> pojoList = new ArrayList<ProductEntity>();
		for (ProductBean productBean : productBeans) {
			ProductEntity entity = new ProductEntity();
			entity.setBrandName(productBean.getBrandName());
			entity.setCategory(productBean.getCategory());
			entity.setModelNumber(productBean.getModelNumber());
			entity.setName(productBean.getName());
			entity.setPrice(productBean.getPrice());
			entity.setWebsite(productBean.getWebsite());
			if (!CommonUtils.isNull(productBean.getUniqueIdentifier()))
				entity.setUniqueIdentifier(productBean.getUniqueIdentifier());
			pojoList.add(entity);
		}
		return pojoList;
	}

}
