package com.relayr.pcs.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.relayr.pcs.service.DataIngestionService;
import com.relayr.pcs.service.ProductComparisonService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductServiceImportControllerTest {

	@Mock
	ProductComparisonService productService;

	@Mock
	DataIngestionService processor;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testUploadingCsvFile() throws Exception {
		String fileName = "test.csv";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		MockMultipartFile csvFile = new MockMultipartFile("file", fileName, MediaType.MULTIPART_FORM_DATA_VALUE,
				bytes);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/v1/pcs/save/products").file(csvFile);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getStatus() == 200);
	}
	
	@Test
	public void testUploadingJsonFile() throws Exception {
		String fileName = "test.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		MockMultipartFile jsonFile = new MockMultipartFile("file", fileName, MediaType.MULTIPART_FORM_DATA_VALUE,
				bytes);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/v1/pcs/save/products").file(jsonFile);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getStatus() == 200);
	}
	
	@Test
	public void testSavingJsonData() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/pcs/save/products").param("json", "[\r\n" + 
				"  {\r\n" + 
				"    \"brandName\": \"string\",\r\n" + 
				"    \"category\": \"string\",\r\n" + 
				"    \"highPrice\": 0,\r\n" + 
				"    \"lowPrice\": 0,\r\n" + 
				"    \"modelNumber\": \"string\",\r\n" + 
				"    \"name\": \"string\",\r\n" + 
				"    \"price\": 0,\r\n" + 
				"    \"uniqueIdentifier\": \"string\",\r\n" + 
				"    \"website\": \"string\"\r\n" + 
				"  }\r\n" + 
				"]");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getStatus() == 200);
	}
	
	@Test
	public void testSavingInvalidJsonData() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/pcs/save/products").param("json", "[\r\n" + 
				"  {\r\n" + 
				"    \"brandName\": \"string\",\r\n" + 
				"    \"category\": \"string\",\r\n" + 
				"    \"highPrice\": 0,\r\n" + 
				"    \"lowPrice\": 0,\r\n" + 
				"    \"modelNumber\": \"string\",\r\n" + 
				"    \"name\": \"string\",\r\n" + 
				"    \"price\": 0,\r\n" + 
				"    \"uniqueIdentifier\": \"string\",\r\n" + 
				"    \"website\": \"string\"\r\n" + 
				"  " + 
				"]");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getStatus() == 500);
		assertTrue(result.getResponse().getContentAsString().equals("{\"message\":\"Json is not Valid\",\"errorCode\":\"APP06\"}"));
	}
	
	@Test
	public void testUploadingEmptyCsvFile() throws Exception {
		String fileName = "test_empty.csv";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		MockMultipartFile csvFile = new MockMultipartFile("file", fileName, MediaType.MULTIPART_FORM_DATA_VALUE,
				bytes);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/v1/pcs/save/products").file(csvFile);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.err.println(result.getResponse().getStatus());
		System.err.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getStatus() == 500);
		assertTrue(result.getResponse().getContentAsString().equals("{\"message\":\"File is empty\",\"errorCode\":\"APP03\"}"));

	}
	
	@Test
	public void testUploadingEmptyJsonFile() throws Exception {
		String fileName = "test_empty.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		MockMultipartFile csvFile = new MockMultipartFile("file", fileName, MediaType.MULTIPART_FORM_DATA_VALUE,
				bytes);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/v1/pcs/save/products").file(csvFile);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.err.println(result.getResponse().getStatus());
		System.err.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getStatus() == 500);
		assertTrue(result.getResponse().getContentAsString().equals("{\"message\":\"File is empty\",\"errorCode\":\"APP03\"}"));

	}
	
	@Test
	public void testImportingFromDb() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/pcs/save/products")
				.param("jdbcString", "jdbc:postgresql://localhost:5432/postgres")
				.param("schema", "public")
				.param("table", "products");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getStatus() == 200);
	}
}
