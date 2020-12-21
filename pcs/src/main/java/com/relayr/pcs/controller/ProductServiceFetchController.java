package com.relayr.pcs.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.service.DataIngestionService;
import com.relayr.pcs.service.DataStandardizer;
import com.relayr.pcs.service.ProductComparisonService;
import com.relayr.pcs.util.CommonUtils;

import io.swagger.annotations.Api;

/**
 * @author asharma2
 *
 */
@RestController
@RequestMapping("/api/v1/pcs/")
@Api(value = "Online Store")
public class ProductServiceFetchController {

	@Autowired
	ProductComparisonService productService;

	@Autowired
	DataIngestionService processor;

	DataStandardizer dataIngestor;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceFetchController.class);


	/**
	 * Api for fitering products based on below prameters
	 * 
	 * @param name
	 * @param category
	 * @param brandName
	 * @param website
	 * @param lowPrice
	 * @param highPrice
	 * @param modelNumber
	 * @return
	 */
	@GetMapping("find/products")
	public ResponseEntity<List<ProductBean>> getProducts(@RequestParam(required = false) String name,
			@RequestParam(required = false) String category,
			@RequestParam(required = false, name = "brand") String brandName,
			@RequestParam(required = false) String website,
			@RequestParam(required = false, name = "low") Double lowPrice,
			@RequestParam(required = false, name = "high") Double highPrice,
			@RequestParam(required = false, name = "modelNumber") String modelNumber) {

		ProductBean requestObject = new ProductBean();
		requestObject.setBrandName(brandName);
		requestObject.setCategory(category);
		if (CommonUtils.isNull(highPrice))
			highPrice = 0.0;
		if (CommonUtils.isNull(lowPrice))
			lowPrice = 0.0;
		requestObject.setHighPrice(highPrice);
		requestObject.setLowPrice(lowPrice);
		requestObject.setModelNumber(modelNumber);
		requestObject.setName(name);
		requestObject.setWebsite(website);
		LOGGER.info("Fetch Request for Products : "+requestObject.toString());
		ResponseEntity<List<ProductBean>> response = new ResponseEntity<List<ProductBean>>(
				productService.getProducts(requestObject), HttpStatus.OK);
		return response;
	}

}
