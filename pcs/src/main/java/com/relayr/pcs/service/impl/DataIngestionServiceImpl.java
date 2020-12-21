package com.relayr.pcs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.relayr.pcs.constants.Constants;
import com.relayr.pcs.service.DataIngestionService;
import com.relayr.pcs.service.DataStandardizer;
import com.relayr.pcs.util.CommonUtils;

/**
 * @author asharma2
 *
 */
@Service
public class DataIngestionServiceImpl implements DataIngestionService {

	@Autowired
	@Qualifier("CsvService")
	DataStandardizer dataIngestionCsv;

	@Autowired
	@Qualifier("JsonService")
	DataStandardizer dataIngestionJson;

	@Autowired
	@Qualifier("DbService")
	DataStandardizer dataIngestionDb;

	@Autowired
	@Qualifier("HttpService")
	DataStandardizer dataIngestionHttp;

	private static final Logger LOGGER = LoggerFactory.getLogger(DataIngestionServiceImpl.class);

	/**
	 * Returns Ingestor reference based on input type (either Csv File/Json
	 * File/JDBC endpoint/Rest endpoint)
	 */
	public DataStandardizer getIngestor(String fileName) {
		String[] fileFrags = null;
		String extension = null;
		if (!CommonUtils.isNull(fileName) && fileName.toLowerCase().trim().contains(Constants.JDBC)) {
			fileFrags = fileName.split(":");
			extension = fileFrags[0];
			if (!CommonUtils.isNull(extension) && extension.toLowerCase().trim().equals(Constants.JDBC)) {
				LOGGER.info("Ingestion type is : JDBC Connection");
				return dataIngestionDb;
			}
		} else if (!CommonUtils.isNull(fileName) && fileName.toLowerCase().trim().contains(Constants.HTTP)) {
			fileFrags = fileName.split(":");
			extension = fileFrags[0];
			if (!CommonUtils.isNull(extension) && (extension.toLowerCase().trim().equals(Constants.HTTP)
					|| extension.toLowerCase().trim().equals(Constants.HTTPS))) {
				LOGGER.info("Ingestion type is : REST Endpoint");
				return dataIngestionHttp;
			}
		} else if (!CommonUtils.isNull(fileName) && fileName.equals(Constants.JSON_FORMAT)) {
			LOGGER.info("Ingestion type is : Json Text");
			return dataIngestionJson;
		} else if (!CommonUtils.isNull(fileName)) {
			fileFrags = fileName.split("\\.");
			extension = fileFrags[fileFrags.length - 1];
			LOGGER.info("Ingestion type is : File Upload");
			if (!CommonUtils.isNull(extension) && extension.toLowerCase().trim().equals(Constants.CSV_FORMAT)) {
				LOGGER.info("File type is : CSV");
				return dataIngestionCsv;
			} else if (!CommonUtils.isNull(extension) && extension.toLowerCase().trim().equals(Constants.JSON_FORMAT)) {
				LOGGER.info("File type is : JSON");
				return dataIngestionJson;
			} else {
				LOGGER.info("File type is : " + extension);
				LOGGER.info("File type " + extension + " is not yet supported");
			}
		}
		return null;
	}
}
