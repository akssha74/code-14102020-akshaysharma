package com.relayr.pcs.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.constants.LoggingConstants;
import com.relayr.pcs.entity.ProductEntity;
import com.relayr.pcs.repository.ProductRepositoryCustom;
import com.relayr.pcs.util.CommonUtils;
import com.replayr.pcs.logging.GlobalLogger;

/**
 * @author asharma2
 *
 */
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Fetches products from Database based on the request recieved
	 * 
	 * @param productBean
	 * @return List of database entity objects
	 */
	@Override
	public List<ProductEntity> getProducts(ProductBean productBean) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductEntity> criteria = cb.createQuery(ProductEntity.class);
		Root<ProductEntity> productRoot = criteria.from(ProductEntity.class);
		List<Predicate> conditions = new ArrayList<>();
		GlobalLogger.log(Level.INFO, LoggingConstants.FILTERING_CRITERIA);
		if (!CommonUtils.isNull(productBean.getName())) {
			GlobalLogger.log(Level.INFO, LoggingConstants.NAME + productBean.getName());
			conditions.add(cb.equal(productRoot.get(Constants.NAME), productBean.getName()));
		} else {
			// DO NOTHING
		}
		if (!CommonUtils.isNull(productBean.getModelNumber())) {
			GlobalLogger.log(Level.INFO, LoggingConstants.MODEL_NUMBER + productBean.getModelNumber());
			conditions.add(cb.equal(productRoot.get(Constants.ENT_MODEL_NUMBER), productBean.getModelNumber()));
		} else {
			// DO NOTHING
		}
		if (!CommonUtils.isNull(productBean.getBrandName())) {
			GlobalLogger.log(Level.INFO, LoggingConstants.BRAND_NAME + productBean.getBrandName());
			conditions.add(cb.equal(productRoot.get(Constants.ENT_BRAND_NAME), productBean.getBrandName()));
		} else {
			// DO NOTHING
		}
		if (!CommonUtils.isNull(productBean.getCategory())) {
			GlobalLogger.log(Level.INFO, LoggingConstants.CATEGORY + productBean.getCategory());
			conditions.add(cb.equal(productRoot.get(Constants.CATEGORY), productBean.getCategory()));
		} else {
			// DO NOTHING
		}
		if (!CommonUtils.isNull(productBean.getWebsite())) {
			GlobalLogger.log(Level.INFO, LoggingConstants.WEBSITE + productBean.getWebsite());
			conditions.add(cb.equal(productRoot.get(Constants.WEBSITE), productBean.getWebsite()));
		} else {
			// DO NOTHING
		}
		if (productBean.getHighPrice() != 0.0 || productBean.getLowPrice() != 0.0) {
			GlobalLogger.log(Level.INFO,
					LoggingConstants.PRICE_BETWEEN + productBean.getLowPrice() + "," + productBean.getHighPrice());
			conditions.add(cb.between(productRoot.get(Constants.PRICE), productBean.getLowPrice(),
					productBean.getHighPrice()));
		} else {
			// DO NOTHING
		}
		if (conditions.isEmpty()) {
			GlobalLogger.log(Level.INFO, LoggingConstants.NO_FILTERS);
			criteria.select(productRoot);
		} else {
			criteria.where(cb.and(conditions.toArray(new Predicate[] {})));
		}
		return entityManager.createQuery(criteria).getResultList();
	}

}
