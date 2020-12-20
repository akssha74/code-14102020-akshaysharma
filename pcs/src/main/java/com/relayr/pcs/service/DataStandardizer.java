package com.relayr.pcs.service;

import java.util.List;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.exception.CustomException;

public interface DataStandardizer {

	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException;
}
