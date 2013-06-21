package com.zuni.serviceprovider.report.domain;

import java.util.Map;

public class ReportRequestObject {

	private String reportName;
	private String fullQueryId;
	private Map<String, Object> queryParameters;
	private Integer sortColumn;
	private String sortOrder;
	private Integer offset;
	private Integer size;

	public ReportRequestObject(Map<String, Object> queryParameters, Integer sortColumn, String sortOrder, Integer offset, Integer size) {
		super();
		this.queryParameters = queryParameters;
		this.sortColumn = sortColumn;
		this.sortOrder = sortOrder;
		this.offset = offset;
		this.size = size;
	}

	public ReportRequestObject(String reportName, Map<String, Object> queryParameters, Integer sortColumn, String sortOrder, Integer offset, Integer size) {
		super();
		this.reportName = reportName;
		this.queryParameters = queryParameters;
		this.sortColumn = sortColumn;
		this.sortOrder = sortOrder;
		this.offset = offset;
		this.size = size;
	}

	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getFullQueryId() {
		return this.fullQueryId;
	}

	public void setFullQueryId(String fullQueryId) {
		this.fullQueryId = fullQueryId;
	}

	public Map<String, Object> getQueryParameters() {
		return this.queryParameters;
	}

	public void setQueryParameters(Map<String, Object> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public Integer getSortColumn() {
		return this.sortColumn;
	}

	public void setSortColumn(Integer sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getOffset() {
		return this.offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
