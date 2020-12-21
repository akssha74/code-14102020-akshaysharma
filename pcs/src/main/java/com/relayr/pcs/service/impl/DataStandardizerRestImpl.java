package com.relayr.pcs.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.ErrorMessages;
import com.relayr.pcs.exception.CustomException;
import com.relayr.pcs.service.DataStandardizer;

/**
 * @author asharma2
 *
 */
@Service
@Qualifier("HttpService")
public class DataStandardizerRestImpl implements DataStandardizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataStandardizerRestImpl.class);

	@Autowired
	Environment env;

	/**
	 *@returns beanList by pulling data from Rest endpoint
	 */
	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException {
		String connection = new String(bytes);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		LOGGER.info("Connection Endpoint : "+connection);
		HttpGet request = new HttpGet(connection);
		ObjectMapper mapper = new ObjectMapper();
		try {
			CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				List<ProductBean> beanList = mapper.readValue(result, new TypeReference<List<ProductBean>>(){});
				return beanList;
			}
		} catch (IOException e) {
			LOGGER.error(ErrorMessages.APP05.message());
			e.printStackTrace();
			throw new CustomException(ErrorMessages.APP05.code(), ErrorMessages.APP05.message());
		}
		return null;
	}
}