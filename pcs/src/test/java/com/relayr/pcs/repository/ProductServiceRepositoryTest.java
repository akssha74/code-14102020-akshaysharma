package com.relayr.pcs.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.entity.ProductEntity;
import com.relayr.pcs.service.DataIngestionService;
import com.relayr.pcs.service.ProductComparisonService;

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
