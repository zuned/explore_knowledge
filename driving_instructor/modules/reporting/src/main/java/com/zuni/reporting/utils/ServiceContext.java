package com.zuni.reporting.utils;

import com.zuni.reporting.core.ReportService;

public interface ServiceContext<K extends ReportService>
{
	public ReportService getService();
	public void setService(K k);
}
