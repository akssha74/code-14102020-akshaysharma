package com.relayr.pcs.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.relayr.pcs.bean.ProductBean;
import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.constants.ErrorMessages;
import com.relayr.pcs.constants.LoggingConstants;
import com.relayr.pcs.exception.CustomException;
import com.relayr.pcs.service.DataStandardizer;
import com.relayr.pcs.util.CommonUtils;
import com.replayr.pcs.logging.GlobalLogger;

/**
 * @author asharma2
 *
 */
@Service
public class DataStandardizerDbImpl implements DataStandardizer {

	@Autowired
	Environment env;

	/**
	 * returns bean list by pulling data from JDBC endpoint
	 */
	@Override
	public List<ProductBean> loadDataToDB(byte[] bytes) throws CustomException {
		String[] connection = new String(bytes).split("\\|");
		String jdbcString = connection[0];
		String schema = connection[1];
		String table = connection[2];
		String driver = connection[3];
		GlobalLogger.log(Level.INFO, LoggingConstants.DRIVER_CLASS + driver);
		List<ProductBean> beanList = new ArrayList<ProductBean>();
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(jdbcString);
			Statement stmt = con.createStatement();
			String query = Constants.SELECT + env.getProperty(Constants.QUERY_COLS) + Constants.FROM + schema + "."
					+ table;
			GlobalLogger.log(Level.INFO, LoggingConstants.QUERY + query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				ProductBean bean = new ProductBean();
				bean.setBrandName(rs.getString(Constants.BRAND_NAME));
				bean.setCategory(rs.getString(Constants.CATEGORY));
				bean.setHighPrice(Double.parseDouble(rs.getString(Constants.PRICE)));
				bean.setLowPrice(Double.parseDouble(rs.getString(Constants.PRICE)));
				bean.setPrice(Double.parseDouble(rs.getString(Constants.PRICE)));
				bean.setModelNumber(rs.getString(Constants.MODEL_NUMBER));
				bean.setName(rs.getString(Constants.NAME));
				bean.setWebsite(rs.getString(Constants.WEBSITE));
				beanList.add(bean);
			}
			return beanList;
		} catch (ClassNotFoundException e) {
			GlobalLogger.log(Level.SEVERE, ErrorMessages.APP01.message());
			throw new CustomException(ErrorMessages.APP01.code(), ErrorMessages.APP01.message());
		} catch (SQLException e) {
			GlobalLogger.log(Level.SEVERE, ErrorMessages.APP02.message());
			throw new CustomException(ErrorMessages.APP02.code(), ErrorMessages.APP02.message());
		}
	}

	@Override
	public boolean verifyInstance(String fileName) throws CustomException {
		if (!CommonUtils.isNull(fileName) && fileName.toLowerCase().trim().startsWith(Constants.JDBC)) {
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