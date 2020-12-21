package com.relayr.pcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
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
@Qualifier("JsonService")
public class DataStandardizerJsonImpl implements DataStandardizer{

	/**
	 *Returns bean list from Json File data
	 */
	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException {
		String completeData = new String(bytes);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ProductBean> beanList = mapper.readValue(completeData, new TypeReference<List<ProductBean>>(){});
			return beanList;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new CustomException(ErrorMessages.APP06.code(),ErrorMessages.APP06.message());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException(ErrorMessages.APP06.code(),ErrorMessages.APP06.message());
		}
	}

}
