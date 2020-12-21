package com.relayr.pcs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.constants.ErrorMessages;
import com.relayr.pcs.exception.CustomException;
import com.relayr.pcs.service.DataIngestionService;
import com.relayr.pcs.service.DataStandardizer;
import com.relayr.pcs.service.ProductComparisonService;
import com.relayr.pcs.util.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

/**
 * @author asharma2
 *
 */
@RestController
@RequestMapping("/api/v1/pcs/")
@Api(value = "Online Store")
public class ProductServiceImportController {

	@Autowired
	ProductComparisonService productService;

	@Autowired
	DataIngestionService processor;

	DataStandardizer dataIngestor;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImportController.class);

	/**
	 * Api for saving file to Database
	 * 
	 * @param file
	 * @return
	 * @throws CustomException
	 */
	@PostMapping(value = "save/products")
	public ResponseEntity<List<ProductBean>> saveProducts(@RequestPart(required = true) MultipartFile file)
			throws CustomException {
		ResponseEntity<List<ProductBean>> response = null;
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		LOGGER.info("Save request for Products Recieved");
		LOGGER.info("Mode : File Upload");
		if (!CommonUtils.isNull(file) && !file.isEmpty()) {
			dataIngestor = processor.getIngestor(file.getOriginalFilename());
			if (CommonUtils.isNull(dataIngestor)) {
				LOGGER.error(ErrorMessages.APP07.message());
				throw new CustomException(ErrorMessages.APP07.code(), ErrorMessages.APP07.message());
			}
			try {
				beanList = dataIngestor.loadDataToDB(file.getBytes());
			} catch (IOException e) {
				LOGGER.error(ErrorMessages.APP03.message());
				e.printStackTrace();
				throw new CustomException(ErrorMessages.APP03.code(), ErrorMessages.APP03.message());
			}
		} else {
			LOGGER.error(ErrorMessages.APP03.message());
			throw new CustomException(ErrorMessages.APP03.code(), ErrorMessages.APP03.message());
		}
		response = new ResponseEntity<List<ProductBean>>(productService.saveProducts(beanList), HttpStatus.OK);
		return response;
	}

	/**
	 * Api for saving Json Input to Database
	 * 
	 * @param json
	 * @return
	 * @throws CustomException
	 */
	@PostMapping(value = "save/products", params = { "json" })
	public ResponseEntity<List<ProductBean>> saveProducts(@RequestParam(value = "json") String json)
			throws CustomException {
		LOGGER.info("Save request for Products Recieved");
		LOGGER.info("Mode : Json Text");
		ResponseEntity<List<ProductBean>> response = null;
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		dataIngestor = processor.getIngestor(Constants.JSON_FORMAT);
		if (CommonUtils.isNull(dataIngestor)) {
			LOGGER.error(ErrorMessages.APP01.message());
			throw new CustomException(ErrorMessages.APP01.code(), ErrorMessages.APP01.message());
		}
		beanList = dataIngestor.loadDataToDB(json.getBytes());
		response = new ResponseEntity<List<ProductBean>>(productService.saveProducts(beanList), HttpStatus.OK);
		return response;
	}

	/**
	 * Api for saving data from Source database to our service database
	 * 
	 * @param jdbc
	 * @param schema
	 * @param table
	 * @return
	 * @throws CustomException
	 */
	@PostMapping(value = "save/products", params = { "jdbcString", "schema", "table" })
	public ResponseEntity<List<ProductBean>> saveProducts(@RequestParam(value = "jdbcString") String jdbc,
			@RequestParam(value = "schema") String schema, @RequestParam(value = "table") String table)
			throws CustomException {
		LOGGER.info("Save request for Products Recieved");
		LOGGER.info("Mode : JDBC Connection");
		ResponseEntity<List<ProductBean>> response = null;
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		dataIngestor = processor.getIngestor(jdbc);
		if (CommonUtils.isNull(dataIngestor)) {
			LOGGER.error(ErrorMessages.APP01.message());
			throw new CustomException(ErrorMessages.APP01.code(), ErrorMessages.APP01.message());
		}
		String driver = CommonUtils.getDriver(jdbc, schema, table);
		beanList = dataIngestor.loadDataToDB(driver.getBytes());
		response = new ResponseEntity<List<ProductBean>>(productService.saveProducts(beanList), HttpStatus.OK);
		return response;
	}

	/**
	 * Api for saving data from given endpoint to our DB
	 * 
	 * @param endpoint
	 * @param endpoint2 (ignore)
	 * @return
	 * @throws CustomException
	 */
	@PostMapping(value = "save/products", params = { "endpoint" })
	public ResponseEntity<List<ProductBean>> saveProducts(@RequestParam(value = "endpoint") String endpoint,
			@ApiParam(hidden = true) @RequestParam(value = "endpoint2", required = false) String endpoint2)
			throws CustomException {
		LOGGER.info("Save request for Products Recieved");
		LOGGER.info("Mode : REST Endpoint");
		ResponseEntity<List<ProductBean>> response = null;
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		dataIngestor = processor.getIngestor(endpoint);
		if (CommonUtils.isNull(dataIngestor)) {
			LOGGER.error(ErrorMessages.APP05.message());
			throw new CustomException(ErrorMessages.APP05.code(), ErrorMessages.APP05.message());
		}
		beanList = dataIngestor.loadDataToDB(endpoint.getBytes());
		response = new ResponseEntity<List<ProductBean>>(productService.saveProducts(beanList), HttpStatus.OK);
		return response;
	}

}
