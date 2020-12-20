package com.relayr.pcs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.service.DataIngestionService;
import com.relayr.pcs.service.DataStandardizer;
import com.relayr.pcs.util.CommonUtils;

@Service
public class DataIngestionServiceImpl implements DataIngestionService{

	@Autowired
	@Qualifier("CsvService")
	DataStandardizer dataIngestionCsv;

	@Autowired
	@Qualifier("JsonService")
	DataStandardizer dataIngestionJson;
	
	@Autowired
	@Qualifier("DbService")
	DataStandardizer dataIngestionDb;

	public DataStandardizer getIngestor(String fileName) {
		String[] fileFrags = null;
		String extension = null;
		if(!CommonUtils.isNull(fileName)) {
			fileFrags = fileName.split(":");
			extension = fileFrags[0];
			if (!CommonUtils.isNull(extension) && extension.toLowerCase().trim().equals(Constants.JDBC))
				return dataIngestionDb;		
		}
		if (!CommonUtils.isNull(fileName)) {
			fileFrags = fileName.split("\\.");
			extension = fileFrags[fileFrags.length - 1];
			if (!CommonUtils.isNull(extension) && extension.toLowerCase().trim().equals(Constants.CSV_FORMAT))
				return dataIngestionCsv;
			else if (!CommonUtils.isNull(extension) && extension.toLowerCase().trim().equals(Constants.JSON_FORMAT))
				return dataIngestionJson;
			}
		else
			return dataIngestionJson;
		return null;
	}
}
