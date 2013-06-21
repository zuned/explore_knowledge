package com.zuni.serviceprovider.web.fascade;

import java.util.List;
import java.util.Map;

import com.zuni.serviceprovider.domain.ReportForm;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface ReportFascadeSerivce {

	List<ReportForm> findReportByName(String reportName, String pageNumber, String pageSize, String sortColumnNumber, String order) throws BusinessException;

	Map<String, Object> getReportData(ReportRequestObject rROb) throws BusinessException;

}
