package com.zuni.reporting.web.jasper.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zuni.reporting.core.Report;
import com.zuni.reporting.core.ReportService;
import com.zuni.reporting.utils.ServiceContext;
import com.zuni.reporting.web.core.AbstractReportController;
import com.zuni.reporting.web.domain.DataList;

@Controller
@RequestMapping("/jasperReport")
public class ReportingController implements AbstractReportController {

	ServiceContext<ReportService> reportServiceContext;
	
	private static Logger logger = LoggerFactory.getLogger(ReportingController.class);

	public ServiceContext<ReportService> getReportServiceContext() {
		return reportServiceContext;
	}

	public void setReportServiceContext(ServiceContext<ReportService> reportServiceContext) {
		this.reportServiceContext = reportServiceContext;
	}

	@Override
	@RequestMapping(value = "/createReport", method = RequestMethod.GET)
	public @ResponseBody
	String createReport(Model model, HttpServletRequest request) throws Exception {
		try {
			ReportService reportService = reportServiceContext.getService();
			Report report = reportService.initReport(request);
			return reportService.createReport(report);
		} catch (Exception e) {
			logger.error("Error while creating report" + e);
			throw e;
		}
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> export(Model model, @RequestParam(value = "reportName") String reportName, @RequestParam(value = "format") String format,
			HttpServletRequest request) throws Exception {
		try {
			ReportService reportService = reportServiceContext.getService();
			Report report = reportService.initReport(request);
			HttpHeaders responseHeaders = new HttpHeaders();
			return new ResponseEntity<byte[]>(reportService.export(report, responseHeaders).getBody(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while creating report" + e);
			throw e;
		}
	}

	@Override
	@RequestMapping(value = "/getReports", method = RequestMethod.GET)
	public ModelAndView getReportsList(HttpServletRequest request) {
		ReportService reportService = reportServiceContext.getService();
		ModelAndView mView = new ModelAndView("index");
		List<String> reportList = reportService.getReportsList();
		mView.addObject("reportList", reportList);
		return mView;
	}

	@Override
	public ResponseEntity<byte[]> export(Model model, String params, String reportName, String format, HttpServletRequest request) throws JRException {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/createReportNew", method = RequestMethod.GET)
	public <T> String createReport(HttpServletRequest request) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			DataList<T> dataList = (DataList<T>) request.getAttribute("dataList");
			ReportService reportService = reportServiceContext.getService();
			Report report = reportService.initReport(request);
			return reportService.createReport(report, dataList.getData(), dataList.getTotalRecords());
		} catch (Exception e) {
			logger.error("Error while creating report" + e);
			throw e;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/exportNew", method = RequestMethod.GET)
	public <T> ResponseEntity<byte[]> export(@RequestParam(value = "reportName") String reportName, @RequestParam(value = "format") String format, HttpServletRequest request)
			throws Exception {
		try {
			@SuppressWarnings("unchecked")
			DataList<T> dataList = (DataList<T>) request.getAttribute("dataList");
			ReportService reportService = reportServiceContext.getService();
			Report report = reportService.initReport(request);
			HttpHeaders responseHeaders = new HttpHeaders();
			return new ResponseEntity<byte[]>(reportService.export(report, responseHeaders, dataList.getData()).getBody(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while creating report" + e);
			throw e;
		}
	}

}
