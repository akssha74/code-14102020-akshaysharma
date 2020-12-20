package com.relayr.pcs.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.ErrorMessages;
import com.relayr.pcs.exception.CustomException;
import com.relayr.pcs.service.DataStandardizer;

@Service
@Qualifier("DbService")
public class DataStandardizerDbImpl implements DataStandardizer {

	@Autowired
	Environment env;

	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException {
		String[] connection = new String(bytes).split("\\|");
		String jdbcString = connection[0];
		String schema = connection[1];
		String table = connection[2];
		String driver = connection[3];
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(jdbcString);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT " + env.getProperty("query.cols") + " FROM " + schema + "." + table);
			while (rs.next()) {
				ProductBean bean = new ProductBean();
				bean.setBrandName(rs.getString("brand_name"));
				bean.setCategory(rs.getString("category"));
				bean.setHighPrice(Double.parseDouble(rs.getString("price")));
				bean.setLowPrice(Double.parseDouble(rs.getString("price")));
				bean.setPrice(Double.parseDouble(rs.getString("price")));
				bean.setModelNumber(rs.getString("model_number"));
				bean.setName(rs.getString("name"));
				bean.setWebsite(rs.getString("website"));
				beanList.add(bean);
			}
			return beanList;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new CustomException(ErrorMessages.APP01.code(), ErrorMessages.APP01.message());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException(ErrorMessages.APP02.code(), ErrorMessages.APP02.message());
		}
	}
}