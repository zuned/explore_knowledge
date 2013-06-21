package com.zuni.reporting.web.core;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

//@Controller
public interface AbstractReportController {

	//public abstract ResponseEntity<byte[]> createReport(Model model,HttpServletRequest request);

	public ModelAndView getReportsList(HttpServletRequest request);

	public ResponseEntity<byte[]> export(Model model, String params, String reportName, String format, HttpServletRequest request) throws JRException;

	public String createReport(Model model, HttpServletRequest request) throws Exception;

	/* Commented out during refactoring*/
	//public <T> String createReport(Model model, HttpServletRequest request, DataList<T> dataList) throws Exception;

	//public <T> ResponseEntity<byte[]> export(Model model, String params, String reportName, String format, HttpServletRequest request, DataList<T> dataList) throws JRException;
}
