package com.relayr.pcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.service.DataStandardizer;

@Service
@Qualifier("JsonService")
public class DataStandardizerJsonImpl implements DataStandardizer{

	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) {
		String completeData = new String(bytes);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ProductBean> beanList = mapper.readValue(completeData, new TypeReference<List<ProductBean>>(){});
			return beanList;
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
