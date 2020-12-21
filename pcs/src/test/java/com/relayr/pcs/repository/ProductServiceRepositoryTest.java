package com.relayr.pcs.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.relayr.pcs.entity.ProductEntity;

@ActiveProfiles("test")
@DataJpaTest
public class ProductServiceRepositoryTest {

	@Autowired
	ProductRepository productRepo;

	@Test
	public void testGettingResultWithAllValues() throws Exception {

		ProductEntity e2 = new ProductEntity();
		e2.setBrandName("brandNames");
		e2.setCategory("category");
		e2.setModelNumber("modelNum123");
		e2.setName("name");
		e2.setPrice(1.0);
		e2.setUniqueIdentifier("u1234567");
		e2.setWebsite("WEB");
		productRepo.save(e2);
		assertTrue(productRepo.findByUniqueIdentifier("u1234567").equals(e2));
	}

}
