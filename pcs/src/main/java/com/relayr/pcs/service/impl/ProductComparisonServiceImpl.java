package com.relayr.pcs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.LoggingConstants;
import com.relayr.pcs.entity.ProductEntity;
import com.relayr.pcs.repository.ProductRepository;
import com.relayr.pcs.service.ProductComparisonService;
import com.relayr.pcs.util.CommonUtils;
import com.replayr.pcs.logging.GlobalLogger;

/**
 * @author asharma2
 *
 */
@Service
public class ProductComparisonServiceImpl implements ProductComparisonService {
	
	@Autowired
	ProductRepository productRepository;

	/**
	 *Fetch the list of products from database
	 *@param productBean
	 */
	@Override
	public List<ProductBean> getProducts(ProductBean productBean) {
		GlobalLogger.log(Level.INFO,LoggingConstants.FETCH_PRODUCTS);
		return convertProductEntityToProductBean(productRepository.getProducts(productBean));
	}

	/**
	 * Converts list of database entity objects to bean objects
	 * @param productList
	 * @return beanList
	 */
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

	/**
	 *Save list of products into database
	 */
	@Override
	public List<ProductBean> saveProducts(List<ProductBean> productBeans) {
		GlobalLogger.log(Level.INFO,LoggingConstants.SAVE_PRODUCTS);
		List<ProductEntity> pojoList = convertProductBeanToProductEntity(productBeans);
		return convertProductEntityToProductBean(productRepository.saveAll(pojoList));
	}

	/**
	 * Converts request bean object into database entity object
	 * @param productBeans
	 * @return pojoList
	 */
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
