package com.zuni.reporting.test;

import com.zuni.reporting.core.Report;
import com.zuni.reporting.request.JasperReport;
import com.zuni.reporting.service.JasperReportService;

public class JasperReportTest {

	public static void main(String args[]) {
		JasperReportService jService = new JasperReportService();
		jService.setCredentials("zuned", "TestCompany", "localhost", 8080);
		Report report = new JasperReport();
		report.setReportName("paginatedReport");
		report.setPageNumber(1);
		report.setType("JRPRINT");
		/*ResponseEntity<byte[]> x=jService.createReport(report);
		JRExporter jrExporter = new JRJsonExporter();
		InputStream is = new ByteArrayInputStream(x.getBody());
		JasperPrint print = (JasperPrint) JRLoader.loadObject(is);
		String reportName="paginatedReport";
		String fileName="C:/tem/"+reportName+new Date().getTime()+".csv";
		logger.info(print.getPropertiesMap());
		StringBuffer a=new StringBuffer();
		jrExporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER,a);
		jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		jrExporter.setParameter(JRExporterParameter.PAGE_INDEX, 2);
		jrExporter.exportReport();
		logger.info(a);*/
	}
}
