package com.relayr.pcs.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.entity.ProductEntity;
import com.relayr.pcs.repository.ProductRepositoryCustom;
import com.relayr.pcs.util.CommonUtils;

/**
 * @author asharma2
 *
 */
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepositoryImpl.class);

	/**
	 * Fetches products from Database based on the request recieved
	 * 
	 * @param productBean
	 * @return List of dataabase entity objects
	 */
	@Override
	public List<ProductEntity> getProducts(ProductBean productBean) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductEntity> criteria = cb.createQuery(ProductEntity.class);
		Root<ProductEntity> productRoot = criteria.from(ProductEntity.class);
		List<Predicate> conditions = new ArrayList<>();
		LOGGER.info("Filtering Criteria :");
		if (!CommonUtils.isNull(productBean.getName())) {
			LOGGER.info("name = " + productBean.getName());
			conditions.add(cb.equal(productRoot.get(Constants.NAME), productBean.getName()));
		}
		if (!CommonUtils.isNull(productBean.getModelNumber())) {
			LOGGER.info("model_number = " + productBean.getModelNumber());
			conditions.add(cb.equal(productRoot.get(Constants.ENT_MODEL_NUMBER), productBean.getModelNumber()));
		}
		if (!CommonUtils.isNull(productBean.getBrandName())) {
			LOGGER.info("brand_name = " + productBean.getBrandName());
			conditions.add(cb.equal(productRoot.get(Constants.ENT_BRAND_NAME), productBean.getBrandName()));
		}
		if (!CommonUtils.isNull(productBean.getCategory())) {
			LOGGER.info("category = " + productBean.getCategory());
			conditions.add(cb.equal(productRoot.get(Constants.CATEGORY), productBean.getCategory()));
		}
		if (!CommonUtils.isNull(productBean.getWebsite())) {
			LOGGER.info("website = " + productBean.getWebsite());
			conditions.add(cb.equal(productRoot.get(Constants.WEBSITE), productBean.getWebsite()));
		}
		if (productBean.getHighPrice() != 0.0 || productBean.getLowPrice() != 0.0) {
			LOGGER.info("price between " + productBean.getLowPrice() + "," + productBean.getHighPrice());
			conditions.add(cb.between(productRoot.get(Constants.PRICE), productBean.getLowPrice(),
					productBean.getHighPrice()));
		}
		if (conditions.isEmpty()) {
			LOGGER.info("No Filters");
			criteria.select(productRoot);
		} else
			criteria.where(cb.and(conditions.toArray(new Predicate[] {})));
		return entityManager.createQuery(criteria).getResultList();
	}

}
