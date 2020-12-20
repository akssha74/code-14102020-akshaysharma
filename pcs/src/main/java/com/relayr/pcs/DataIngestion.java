package com.relayr.pcs;

import java.util.List;

import com.relayr.pcs.bean.ProductBean;

public interface DataIngestion {

	public List<ProductBean> loadDataToDB(byte[] bytes);
}
