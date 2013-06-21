package com.zuni.reporting.service;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.zuni.reporting.core.Report;
import com.zuni.reporting.core.ReportService;
import com.zuni.reporting.request.JasperReport;
import com.zuni.reporting.utils.JRJsonExporter;
import com.zuni.reporting.utils.JSONExporterParameter;
import com.zuni.reporting.utils.ReportUtils;

public class JasperLibraryService implements ReportService {

	private static Logger logger = LoggerFactory.getLogger(JasperLibraryService.class);

	@Value("${reporting.defaultFont}")
	private String defaultFont;

	private String reportPath;
	private DataSource dataSource;
	private ReportUtils reportUtils;

	private static String fileSeparator = System.getProperty("file.separator");

	public ReportUtils getReportUtils() {
		return this.reportUtils;
	}

	public void setReportUtils(ReportUtils reportUtils) {
		this.reportUtils = reportUtils;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getReportPath() {
		return this.reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Report initReport(HttpServletRequest request) {
		Report report = new JasperReport();
		String type = request.getParameter("format");
		if (type == null || type.trim().equals("")) {
			type = "HTML";
		}
		report.setType(type);
		report.setReportName(request.getParameter("reportName"));
		report.setParameters((HashMap<String, Object>) request.getAttribute("reportingParametersMap"));
		String pageNo = request.getParameter("page_Num");
		if (pageNo == null || pageNo.trim().equals("")) {
			report.setPageNumber(0);
		} else {
			report.setPageNumber(Integer.parseInt(pageNo));
		}
		return report;
	}

	@Override
	public <T> String createReport(Report reportRequest, List<T> list, Long totalResults) throws Exception {

		logger.debug("Entering createReport function in JasperLibraryService");
		StringBuffer a = new StringBuffer();
		try {
			String fileName = this.reportPath + reportRequest.getReportName() + ".jasper";
			//String fileName = this.reportPath  + fileSeparator + reportRequest.getReportName() + ".jasper";
			logger.debug("Report file to be used:" + fileName);

			JRProperties.setProperty("net.sf.jasperreports.default.font.name", this.defaultFont);
			JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", true);

			//fill it in JRBeanCollectionDS
			JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(list);

			Map<String, Object> hm = reportRequest.getParameters();
			JasperPrint print = JasperFillManager.fillReport(fileName, hm, jrbcds);
			logger.debug("Jasper Print object created");

			JRExporter jrExporter = new JRJsonExporter();
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, a);
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			jrExporter.setParameter(JRExporterParameter.PAGE_INDEX, reportRequest.getPageNumber());
			jrExporter.setParameter(JSONExporterParameter.TOTAL_RECORDS, totalResults);
			jrExporter.exportReport();
			logger.debug("Jasper report exported in JSON format");
		} catch (JRException e) {
			logger.error("Error generating report" + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Error report creation" + e.getMessage());
			throw e;
		}
		return a.toString();
	}

	@Override
	public String createReport(Report reportRequest) throws Exception {
		logger.debug("Entering createReport function in JasperLibraryService");
		StringBuffer a = new StringBuffer();
		Connection con = null;
		try {
			logger.debug("Attempting to get data source connection");
			con = this.dataSource.getConnection();
			logger.debug("Data Source connection received");
			String fileName = this.reportPath  + fileSeparator + reportRequest.getReportName() + ".jasper";
			logger.debug("Report file to be used:" + fileName);
			JRExporter jrExporter = new JRJsonExporter();
			Map<String, Object> hm = reportRequest.getParameters();
			JRProperties.setProperty("net.sf.jasperreports.default.font.name", this.defaultFont);
			JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", true);
			JasperPrint print = JasperFillManager.fillReport(fileName, hm, con);
			logger.debug("Jasper Print object created");
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, a);
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			jrExporter.setParameter(JRExporterParameter.PAGE_INDEX, reportRequest.getPageNumber());
			jrExporter.exportReport();
			logger.debug("Jasper report exported in JSON format");
		} catch (SQLException e) {
			logger.error("Error opening connection for the report " + e.getMessage());
			throw e;
		} catch (JRException e) {
			logger.error("Error generating report" + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Error report creation" + e.getMessage());
			throw e;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				logger.error("Error closing the report connection" + e.getMessage());
				throw e;
			}
		}
		return a.toString();
	}

	@Override
	public List<String> getReportsList() {
		return null;
	}

	@Override
	public List<String> getReportsList(String repositoryPath) {
		return null;
	}

	@Override
	public ResponseEntity<byte[]> export(Report reportRequest, HttpHeaders responseHeaders) throws Exception {
		Connection con = null;
		ByteArrayOutputStream a = null;
		try {
			con = this.dataSource.getConnection();
			String fileName = this.reportPath + fileSeparator + reportRequest.getReportName() + ".jasper";
			JRExporter jrExporter = getReportExporter(reportRequest.getType());
			Map<String, Object> hm = reportRequest.getParameters();
			JRProperties.setProperty("net.sf.jasperreports.default.font.name", this.defaultFont);
			JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", true);
			JasperPrint print = JasperFillManager.fillReport(fileName, hm, con);
			
			a = new ByteArrayOutputStream();
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, a);
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			jrExporter.setParameter(JRExporterParameter.PAGE_INDEX, reportRequest.getPageNumber());
			jrExporter.exportReport();

			ResponseEntity<byte[]> outReport = new ResponseEntity<byte[]>(a.toByteArray(), HttpStatus.OK);
			responseHeaders.set("content-type", this.reportUtils.getOutputTypeMap().get(reportRequest.getType()));
			String outFileName = reportRequest.getReportName() + this.reportUtils.getDateFormat().format(new Date()) + "." + reportRequest.getType();
			responseHeaders.set("Content-disposition", "attachment; filename=" + outFileName);
			return outReport;
		} catch (SQLException e) {
			logger.error("Error opening connection for the report " + e.getMessage());
			throw e;
		} catch (JRException e) {
			logger.error("Error in generating report" + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Error in report creation" + e.getMessage());
			throw e;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (a != null) {
					a.close();
				}
			} catch (Exception e) {
				logger.error("Error closing the report connection" + e.getMessage());
				throw e;
			}
		}
	}

	private JRExporter getReportExporter(String type) {
		if("XSL".equalsIgnoreCase(type)) {
			return  new JRXlsExporter();
		} else if("PDF".equalsIgnoreCase(type)) {
			return new JRPdfExporter();
		}
		return  new JRXlsExporter();
	}

	@Override
	public <T> ResponseEntity<byte[]> export(Report reportRequest, HttpHeaders responseHeaders, List<T> list) throws Exception {
		ByteArrayOutputStream a = null;
		try {
			//fill it in JRBeanCollectionDS
			JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(list);

			JRProperties.setProperty("net.sf.jasperreports.default.font.name", this.defaultFont);
			JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", true);

			String fileName = this.reportPath + reportRequest.getReportName() + ".jasper";
			Map<String, Object> hm = reportRequest.getParameters();
			JasperPrint print = JasperFillManager.fillReport(fileName, hm, jrbcds);

			a = new ByteArrayOutputStream();
			JRExporter jrExporter = new JRXlsExporter();
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, a);
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			jrExporter.setParameter(JRExporterParameter.PAGE_INDEX, reportRequest.getPageNumber());
			jrExporter.exportReport();

			ResponseEntity<byte[]> outReport = new ResponseEntity<byte[]>(a.toByteArray(), HttpStatus.OK);
			responseHeaders.set("content-type", this.reportUtils.getOutputTypeMap().get(reportRequest.getType()));
			String outFileName = reportRequest.getReportName() + this.reportUtils.getDateFormat().format(new Date()) + "." + reportRequest.getType();
			responseHeaders.set("Content-disposition", "attachment; filename=" + outFileName);
			return outReport;
		} catch (JRException e) {
			logger.error("Error in generating report" + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Error in report creation" + e.getMessage());
			throw e;
		} finally {
			if (a != null) {
				a.close();
			}
		}
	}
}
