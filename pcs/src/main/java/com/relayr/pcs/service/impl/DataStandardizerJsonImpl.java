package com.relayr.pcs.service.impl;

import java.util.List;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.constants.ErrorMessages;
import com.relayr.pcs.exception.CustomException;
import com.relayr.pcs.service.DataStandardizer;
import com.relayr.pcs.util.CommonUtils;
import com.replayr.pcs.logging.GlobalLogger;

/**
 * @author asharma2
 *
 */
@Service
public class DataStandardizerJsonImpl implements DataStandardizer {

	/**
	 * Returns bean list from Json File data
	 */
	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException {
		String completeData = new String(bytes);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ProductBean> beanList = mapper.readValue(completeData, new TypeReference<List<ProductBean>>() {
			});
			return beanList;
		} catch (JsonMappingException e) {
			GlobalLogger.log(Level.SEVERE, ErrorMessages.APP06.message());
			throw new CustomException(ErrorMessages.APP06.code(), ErrorMessages.APP06.message());
		} catch (JsonProcessingException e) {
			GlobalLogger.log(Level.SEVERE, ErrorMessages.APP06.message());
			throw new CustomException(ErrorMessages.APP06.code(), ErrorMessages.APP06.message());
		}
	}

	@Override
	public boolean verifyInstance(String fileName) throws CustomException {
		String[] fileFrags = fileName.split("\\.");
		String extension = fileFrags[fileFrags.length - 1];
		if (fileName.equals(Constants.JSON_FORMAT)) {
			return true;
		} else if (!CommonUtils.isNull(extension) && extension.toLowerCase().trim().equals(Constants.JSON_FORMAT)) {
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
