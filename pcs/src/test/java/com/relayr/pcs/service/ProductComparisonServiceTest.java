package com.relayr.pcs.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.relayr.pcs.exception.CustomException;
import com.relayr.pcs.service.impl.DataIngestionServiceImpl;
import com.relayr.pcs.service.impl.DataStandardizerCsvImpl;
import com.relayr.pcs.service.impl.DataStandardizerDbImpl;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductComparisonServiceTest {

	@Autowired
	DataIngestionServiceImpl dataIngestionService;

	@Autowired
	DataStandardizerCsvImpl csvService;
	
	@Autowired
	DataStandardizerDbImpl dbService;

	@Test
	public void testForDataStanadardizerObject() {
		assertNull(dataIngestionService.getIngestor(null));
	}

	@Test
	public void testForIncompleteCsv() throws IOException {
		String fileName = "test_incomplete.csv";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		CustomException c = assertThrows(CustomException.class,() -> csvService.loadDataToDB(bytes));
		assertTrue(c.getErrorMessage().equals("This CSV file is not Valid"));
		
		
	}
	
	@Test
	public void testForInvalidCsv() throws IOException {
		String fileName = "test_invalid.csv";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		CustomException c = assertThrows(CustomException.class,() -> csvService.loadDataToDB(bytes));
		assertTrue(c.getErrorMessage().equals("This CSV file is not Valid"));
		
		
	}


}
