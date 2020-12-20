package com.relayr.pcs;

import java.util.ArrayList;
import java.util.List;

import com.relayr.pcs.bean.ProductBean;

public class DataIngestionCSV implements DataIngestion{

	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) {
        String completeData = new String(bytes);
        String[] rows = completeData.split("\n");
        List<ProductBean> beanList = new ArrayList<ProductBean>();
		for(int index = 1;index < rows.length;index++) {
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

}
