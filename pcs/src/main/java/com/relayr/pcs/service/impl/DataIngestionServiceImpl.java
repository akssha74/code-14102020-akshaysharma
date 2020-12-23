package com.relayr.pcs.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
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

	/**
	 * Returns Ingestor reference based on input type (either Csv File/Json
	 * File/JDBC endpoint/Rest endpoint)
	 */
	public DataStandardizer getIngestor(String fileName) {
		if (!CommonUtils.isNull(fileName)) {
			Reflections reflections = new Reflections(ClasspathHelper.forPackage(Constants.ROOT_PACKAGE),
					new SubTypesScanner());
			Set<Class<? extends DataStandardizer>> classes = reflections.getSubTypesOf(DataStandardizer.class);
			Class[] methodParameters = new Class[1];
			methodParameters[0] = String.class;
			for (Class<? extends DataStandardizer> dataStandardizer : classes) {
				try {
					DataStandardizer instance = dataStandardizer.newInstance();
					Method verifyInstance = dataStandardizer.getMethod(Constants.VERIFY_INSTANCE, methodParameters);
					boolean checkClass = (boolean) verifyInstance.invoke(instance, fileName);
					if (checkClass) {
						return dataStandardizer.newInstance();
					}
				} catch (NoSuchMethodException | SecurityException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				} catch (InstantiationException e) {
				}
			}
		}

		return null;
	}
}
