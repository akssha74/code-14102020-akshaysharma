package com.relayr.pcs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.relayr.pcs.DataIngestion;
import com.relayr.pcs.DataIngestionCSV;
import com.relayr.pcs.DataIngestionJson;
import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.service.ProductComparisonService;
import com.relayr.pcs.util.CommonUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/pcs/")
@Api(value = "Online Store")
public class ProductServiceController {

	@Autowired
	ProductComparisonService productService;

	@Autowired
	StringToProductBeanConverter converter;

	DataIngestion dataIngestor;

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
		ResponseEntity<List<ProductBean>> response = new ResponseEntity<List<ProductBean>>(
				productService.getProducts(requestObject), HttpStatus.OK);
		return response;
	}

	@PostMapping("save/products")
	public ResponseEntity<List<ProductBean>> saveProducts(@RequestPart(required = false) String beans,
			@RequestPart(required = false, name = "file") MultipartFile file) {
		ResponseEntity<List<ProductBean>> response = null;
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		if (!CommonUtils.isNull(file) && !file.isEmpty()) {
			String[] fileFrags = file.getOriginalFilename().split("\\.");
			String extension = fileFrags[fileFrags.length - 1];
			if (extension.toLowerCase().trim().equals("csv"))
				dataIngestor = new DataIngestionCSV();
			else if (extension.toLowerCase().trim().equals("json"))
				dataIngestor = new DataIngestionJson();
			try {
				beanList = dataIngestor.loadDataToDB(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			dataIngestor = new DataIngestionJson();
			beanList = dataIngestor.loadDataToDB(beans.getBytes());
		}
		response = new ResponseEntity<List<ProductBean>>(productService.saveProducts(beanList), HttpStatus.OK);
		return response;
	}

}
