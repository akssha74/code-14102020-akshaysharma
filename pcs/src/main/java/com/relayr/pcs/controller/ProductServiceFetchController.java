package com.relayr.pcs.controller;

import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.LoggingConstants;
import com.relayr.pcs.logging.GlobalLogger;
import com.relayr.pcs.service.DataIngestionService;
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

	/**
	 * Api for filtering products based on below parameters
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
		if (CommonUtils.isNull(highPrice)) {
			highPrice = 0.0;
		} else {
			// DO NOTHING
		}
		if (CommonUtils.isNull(lowPrice)) {
			lowPrice = 0.0;
		} else {
			// DO NOTHING
		}
		requestObject.setHighPrice(highPrice);
		requestObject.setLowPrice(lowPrice);
		requestObject.setModelNumber(modelNumber);
		requestObject.setName(name);
		requestObject.setWebsite(website);
		GlobalLogger.log(Level.INFO, LoggingConstants.FETCH_REQUEST + requestObject.toString());
		ResponseEntity<List<ProductBean>> response = new ResponseEntity<List<ProductBean>>(
				productService.getProducts(requestObject), HttpStatus.OK);
		return response;
	}

}
