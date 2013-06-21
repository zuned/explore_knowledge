package com.zuni.reporting.request;

import java.util.ArrayList;
import java.util.List;

import com.zuni.reporting.core.Response;

public class JsonResponse implements Response {

	private int headerCount;
	private int viewResultsCount;
	private long totalResultsCount;
	private List<String> headers = new ArrayList<String>();
	private StringBuffer jsonValue = new StringBuffer();
	private StringBuffer value;

	public StringBuffer getValue() {
		return value;
	}

	public void setValue(StringBuffer value) {
		this.value = value;
	}

	public int getHeaderCount() {
		return headerCount;
	}

	public void setHeaderCount(int headerCount) {
		this.headerCount = headerCount;
	}

	public int getViewResultsCount() {
		return viewResultsCount;
	}

	public void setViewResultsCount(int viewResultsCount) {
		this.viewResultsCount = viewResultsCount;
	}

	public long getTotalResultsCount() {
		return totalResultsCount;
	}

	public void setTotalResultsCount(long totalResultsCount) {
		this.totalResultsCount = totalResultsCount;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public StringBuffer getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(StringBuffer jsonValue) {
		this.jsonValue = jsonValue;
	}
}
