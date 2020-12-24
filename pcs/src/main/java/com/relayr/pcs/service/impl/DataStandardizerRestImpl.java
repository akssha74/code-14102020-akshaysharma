package com.relayr.pcs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.constants.ErrorMessages;
import com.relayr.pcs.constants.LoggingConstants;
import com.relayr.pcs.exception.CustomException;
import com.relayr.pcs.logging.GlobalLogger;
import com.relayr.pcs.service.DataStandardizer;
import com.relayr.pcs.util.CommonUtils;

/**
 * @author asharma2
 *
 */
@Service
public class DataStandardizerRestImpl implements DataStandardizer {

	@Autowired
	Environment env;

	/**
	 * @returns beanList by pulling data from Rest endpoint
	 */
	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException {
		String connection = new String(bytes);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		GlobalLogger.log(Level.INFO, LoggingConstants.REST_ENDPOINT + connection);
		HttpGet request = new HttpGet(connection);
		ObjectMapper mapper = new ObjectMapper();
		try {
			CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				List<ProductBean> beanList = mapper.readValue(result, new TypeReference<List<ProductBean>>() {
				});
				return beanList;
			}
		} catch (IOException e) {
			GlobalLogger.log(Level.SEVERE, ErrorMessages.APP05.message());
			throw new CustomException(ErrorMessages.APP05.code(), ErrorMessages.APP05.message());
		}
		return null;
	}

	@Override
	public boolean verifyInstance(String fileName) throws CustomException {
		String extension = fileName.split(":")[0];
		List<String> restProtocols = new ArrayList<String>(Arrays.asList(Constants.HTTP, Constants.HTTPS));
		if (!CommonUtils.isNull(extension) && restProtocols.contains(extension.toLowerCase().trim())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getName() {
		return getClass().getName();
	}
}