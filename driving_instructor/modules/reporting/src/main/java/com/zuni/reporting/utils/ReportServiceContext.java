package com.zuni.reporting.utils;

import com.zuni.reporting.core.ReportService;

public class ReportServiceContext implements ServiceContext<ReportService> {

	private ReportService service;

	@Override
	public ReportService getService() {
		return service;
	}

	@Override
	public void setService(ReportService service) {
		this.service = service;
	}
}
