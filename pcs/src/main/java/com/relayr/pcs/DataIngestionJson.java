package com.relayr.pcs;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relayr.pcs.bean.ProductBean;

public class DataIngestionJson implements DataIngestion{

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
