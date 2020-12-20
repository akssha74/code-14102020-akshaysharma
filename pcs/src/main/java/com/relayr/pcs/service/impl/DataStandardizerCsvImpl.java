package com.relayr.pcs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.constants.ErrorMessages;
import com.relayr.pcs.exception.CustomException;
import com.relayr.pcs.service.DataStandardizer;

@Service
@Qualifier("CsvService")
public class DataStandardizerCsvImpl implements DataStandardizer {

	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException {
		String completeData = new String(bytes);
		String[] rows = completeData.split("\n");
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		String[] colNames = rows[0].split(",");
		validateCsv(colNames);
		for (int index = 1; index < rows.length; index++) {
			String[] cols = rows[index].split(",");
			ProductBean bean = new ProductBean();
			bean.setBrandName(cols[0]);
			bean.setCategory(cols[1]);
			bean.setModelNumber(cols[2]);
			bean.setName(cols[3]);
			bean.setPrice(Double.parseDouble(cols[4]));
			bean.setHighPrice(Double.parseDouble(cols[4]));
			bean.setLowPrice(Double.parseDouble(cols[4]));
			bean.setWebsite(cols[5]);
			beanList.add(bean);
		}
		return beanList;

	}

	private void validateCsv(String[] colNames) throws CustomException {
		try {
			if (colNames[0].equals(Constants.BRAND_NAME) && colNames[1].equals(Constants.CATEGORY)
					&& colNames[2].equals(Constants.MODEL_NUMBER) && colNames[3].equals(Constants.NAME)
					&& colNames[4].equals(Constants.PRICE) && colNames[5].equals(Constants.WEBSITE)) {
				throw new CustomException(ErrorMessages.APP04.code(), ErrorMessages.APP04.message());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorMessages.APP04.code(), ErrorMessages.APP04.message());
		}
	}

}
