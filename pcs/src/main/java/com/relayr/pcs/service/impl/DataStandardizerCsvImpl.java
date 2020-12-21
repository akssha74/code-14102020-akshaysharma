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
import com.relayr.pcs.util.CommonUtils;

/**
 * @author asharma2
 *
 */
@Service
@Qualifier("CsvService")
public class DataStandardizerCsvImpl implements DataStandardizer {

	/**
	 * Returns bean list from Csv File Data
	 */
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
			bean.setBrandName(CommonUtils.cleanString(cols[0]));
			bean.setCategory(CommonUtils.cleanString(cols[1]));
			bean.setModelNumber(CommonUtils.cleanString(cols[2]));
			bean.setName(CommonUtils.cleanString(cols[3]));
			bean.setPrice(Double.parseDouble(CommonUtils.cleanString(cols[4])));
			bean.setHighPrice(Double.parseDouble(CommonUtils.cleanString(cols[4])));
			bean.setLowPrice(Double.parseDouble(CommonUtils.cleanString(cols[4])));
			bean.setWebsite(CommonUtils.cleanString(cols[5]));
			beanList.add(bean);
		}
		return beanList;

	}

	private void validateCsv(String[] colNames) throws CustomException {
		try {

			if (CommonUtils.cleanString(colNames[0]).equals(Constants.BRAND_NAME)
					&& CommonUtils.cleanString(colNames[1]).equals(Constants.CATEGORY)
					&& CommonUtils.cleanString(colNames[2]).equals(Constants.MODEL_NUMBER)
					&& CommonUtils.cleanString(colNames[3]).equals(Constants.NAME)
					&& CommonUtils.cleanString(colNames[4]).equals(Constants.PRICE)
					&& CommonUtils.cleanString(colNames[5]).equals(Constants.WEBSITE)) {
			} else {
				throw new CustomException(ErrorMessages.APP04.code(), ErrorMessages.APP04.message());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorMessages.APP04.code(), ErrorMessages.APP04.message());
		}
	}

}
