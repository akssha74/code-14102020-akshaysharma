package com.relayr.pcs.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.service.DataIngestionService;
import com.relayr.pcs.service.ProductComparisonService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductServiceFetchControllerTest {

	@Mock
	ProductComparisonService productService;

	@Mock
	DataIngestionService processor;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGettingResultWithAllValues() throws Exception {

		List<ProductBean> list = new ArrayList<ProductBean>();
		String brandNames = "brandNames";
		ProductBean e2 = new ProductBean();
		e2.setBrandName("brandNames");
		e2.setCategory("category");
		e2.setModelNumber("modelNum123");
		e2.setName("name");
		e2.setPrice(1.0);
		e2.setHighPrice(1.0);
		e2.setLowPrice(1.0);
		e2.setUniqueIdentifier("u1234567");
		e2.setWebsite("WEB");
		list.add(e2);
		ProductBean e3 = new ProductBean();
		e3.setBrandName("brandNames");
		e3.setCategory("category");
		e3.setModelNumber("modelNum123");
		e3.setName("name");
		e3.setPrice(1.0);
		e3.setHighPrice(1.0);
		e3.setLowPrice(1.0);
		e3.setUniqueIdentifier("uabcdef");
		e3.setWebsite("WEB");
		list.add(e3);
		ObjectMapper ow = new ObjectMapper();

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/pcs/save/products").param("json",
				ow.writeValueAsString(list));
		mockMvc.perform(requestBuilder).andReturn();
		RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("/api/v1/pcs/find/products")
				.param("brand", brandNames).param("category", "category").param("modelNumber", "modelNum123")
				.param("name", "name").param("low", "0.0").param("high", "2.0").param("website", "WEB")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilderGet).andReturn();
		assertTrue(result.getResponse().getContentAsString().equals(ow.writeValueAsString(list)));
	}

	@Test
	public void testGettingResultWithNullValues() throws Exception {

		List<ProductBean> list = new ArrayList<ProductBean>();
		ProductBean e2 = new ProductBean();
		e2.setBrandName("brandNames");
		e2.setCategory("category");
		e2.setModelNumber("modelNum123");
		e2.setName("name");
		e2.setPrice(1.0);
		e2.setHighPrice(1.0);
		e2.setLowPrice(1.0);
		e2.setUniqueIdentifier("u1234567");
		e2.setWebsite("WEB");
		list.add(e2);
		ProductBean e3 = new ProductBean();
		e3.setBrandName("brandNames");
		e3.setCategory("category");
		e3.setModelNumber("modelNum123");
		e3.setName("name");
		e3.setPrice(1.0);
		e3.setHighPrice(1.0);
		e3.setLowPrice(1.0);
		e3.setUniqueIdentifier("uabcdef");
		e3.setWebsite("WEB");
		list.add(e3);
		ObjectMapper ow = new ObjectMapper();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/pcs/save/products").param("json",
				ow.writeValueAsString(list));
		mockMvc.perform(requestBuilder).andReturn();
		RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("/api/v1/pcs/find/products")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilderGet).andReturn();
		List<ProductBean> beanList = ow.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<ProductBean>>() {
				});
		assertTrue(beanList.size() > 1);

	}
}
