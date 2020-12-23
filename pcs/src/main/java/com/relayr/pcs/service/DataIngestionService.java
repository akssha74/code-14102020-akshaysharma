package com.relayr.pcs.service;

/**
 * @author asharma2
 *
 */
public interface DataIngestionService {
	/**
	 * @param fileName
	 * @return DataStandardizer
	 */
	public DataStandardizer getIngestor(String fileName);
}
