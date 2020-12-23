package com.relayr.pcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relayr.pcs.entity.ProductEntity;

/**
 * @author asharma2
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String>, ProductRepositoryCustom {
	
	/**
	 * @param uniqueIdentifier
	 * @return ProductEntity
	 */
	public ProductEntity findByUniqueIdentifier(String uniqueIdentifier);
}
