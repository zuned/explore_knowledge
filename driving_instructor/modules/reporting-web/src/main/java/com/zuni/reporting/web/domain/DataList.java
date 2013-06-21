package com.zuni.reporting.web.domain;

import java.util.List;

public class DataList<T> {

	private List<T> data;

	private Long totalRecords;

	public DataList() {}

	public DataList(List<T> data) {
		super();
		this.data = data;
	}

	public DataList(List<T> data, Long totalRecords) {
		super();
		this.data = data;
		this.totalRecords = totalRecords;
	}

	public List<T> getData() {
		return this.data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
}
