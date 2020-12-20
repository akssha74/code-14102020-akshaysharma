package com.relayr.pcs.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.entity.ProductEntity;
import com.relayr.pcs.repository.ProductRepositoryCustom;
import com.relayr.pcs.util.CommonUtils;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

		
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<ProductEntity> getProducts(ProductBean productBean) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductEntity> criteria = cb.createQuery(ProductEntity.class);
		Root<ProductEntity> productRoot = criteria.from(ProductEntity.class);
		List<Predicate> conditions = new ArrayList<>();
		if(!CommonUtils.isNull(productBean.getName()))
			conditions.add(cb.equal(productRoot.get("name"),productBean.getName()));
		if(!CommonUtils.isNull(productBean.getModelNumber()))
			conditions.add(cb.equal(productRoot.get("modelNumber"),productBean.getModelNumber()));
		if(!CommonUtils.isNull(productBean.getBrandName()))
			conditions.add(cb.equal(productRoot.get("brandName"),productBean.getBrandName()));
		if(!CommonUtils.isNull(productBean.getCategory()))
			conditions.add(cb.equal(productRoot.get("category"),productBean.getCategory()));
		if(!CommonUtils.isNull(productBean.getWebsite()))
			conditions.add(cb.equal(productRoot.get("website"),productBean.getWebsite()));
		if(productBean.getHighPrice() != 0.0 ||  productBean.getLowPrice() != 0.0)
			conditions.add(cb.between(productRoot.get("price"), productBean.getLowPrice(), productBean.getHighPrice()));
		if(conditions.isEmpty())
			criteria.select(productRoot);
		else
			criteria.where(cb.and(conditions.toArray(new Predicate[] {})));
		return entityManager.createQuery(criteria).getResultList();
	}

}
