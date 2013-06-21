package com.zuni.reporting.utils;

import net.sf.jasperreports.engine.JRExporterParameter;

public class JSONExporterParameter extends JRExporterParameter {

	public JSONExporterParameter(String name) {
		super(name);
	}

	public static final JRExporterParameter TOTAL_RECORDS = new JSONExporterParameter("Total Records");
}
