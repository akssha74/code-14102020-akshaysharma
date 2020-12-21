package com.relayr.pcs.service;

/**
 * @author asharma2
 *
 */
public interface DataIngestionService {
	/**
	 * @param fileName
	 * @return
	 */
	public DataStandardizer getIngestor(String fileName);
}
