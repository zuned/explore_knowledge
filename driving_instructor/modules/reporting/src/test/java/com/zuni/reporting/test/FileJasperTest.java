package com.zuni.reporting.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRTextExporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileJasperTest {

	private static Logger logger = LoggerFactory.getLogger(FileJasperTest.class);

	public static void main(String[] args) {
		String fileName = "C:\\Zuned\\jReports\\report1.jasper";
		String outFileName = "test.pdf";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("carrier_id", 5L);
		try {
			// Fill the report using an empty data source
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
			JasperPrint print = JasperFillManager.fillReport(fileName, hm, con);

			// Create a PDF exporter
			JRExporter exporter = new JRTextExporter();

			// Configure the exporter (set output file name and print object)
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

			// Export the PDF file
			exporter.exportReport();

		} catch (JRException e) {
			logger.error("Exception: ", e);
			System.exit(1);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			System.exit(1);
		}
	}
}
