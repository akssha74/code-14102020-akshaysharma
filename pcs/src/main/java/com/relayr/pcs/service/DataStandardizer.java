package com.relayr.pcs.service;

import java.util.List;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.exception.CustomException;

/**
 * @author asharma2
 *
 */
public interface DataStandardizer {

	/**
	 * @param bytes
	 * @return
	 * @throws CustomException
	 */
	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException;
}
