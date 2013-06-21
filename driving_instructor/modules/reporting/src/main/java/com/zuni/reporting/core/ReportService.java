package com.zuni.reporting.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface ReportService {

	public Report initReport(HttpServletRequest request);

	public String createReport(Report reportRequest) throws Exception;

	public <T> String createReport(Report reportRequest, List<T> list, Long totalRecords) throws Exception;

	public List<String> getReportsList();

	public List<String> getReportsList(String repositoryPath);

	public ResponseEntity<byte[]> export(Report report, HttpHeaders responseHeaders) throws Exception;

	public <T> ResponseEntity<byte[]> export(Report report, HttpHeaders responseHeaders, List<T> list) throws Exception;
}
