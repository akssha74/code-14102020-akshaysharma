package com.relayr.pcs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.service.DataStandardizer;

@Service
@Qualifier("CsvService")
public class DataStandardizerCsvImpl implements DataStandardizer{

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
