package com.zuni.reporting.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ReportUtils {

	private HashMap<String, String> outputTypeMap;
	private SimpleDateFormat dateFormat;

	public HashMap<String, String> getOutputTypeMap() {
		return outputTypeMap;
	}

	public void setOutputTypeMap(HashMap<String, String> outputTypeMap) {
		this.outputTypeMap = outputTypeMap;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setSdf(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
}
