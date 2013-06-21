package com.zuni.serviceprovider.report;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;
import com.zuni.serviceprovider.utils.DataTableUtils;
import com.zuni.serviceprovider.web.fascade.ReportFascadeSerivce;

@Controller
@RequestMapping("/admin/reports")
public class AdminReportController {
	
	@Autowired
	private ReportFascadeSerivce reportFascadeSerivce;
	
/**All Reports Can be defined in DB which can be use to display dynamically **/
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String showDashBoard(Model model) {
		return "admin/reports";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public  String getReports(Locale locale, HttpServletRequest request, HttpSession session , Model model) throws UnsupportedEncodingException, BusinessException {
		Map<String, Object> reportingParametersMap = setupParametersMap(request);

		request.setAttribute("reportingParametersMap", reportingParametersMap);
		Map<String, Object> dataTableMap = DataTableUtils.convertDataTableParamsToQueryParams(request);

		String reportName = request.getParameter("reportName");
		ReportRequestObject rROb = new ReportRequestObject(reportingParametersMap, (Integer) dataTableMap.get("sortColumn"), (String) dataTableMap.get("sortOrder"),
				(Integer) dataTableMap.get("start"), (Integer) dataTableMap.get("length"));
		rROb.setReportName(reportName);
//		Map<String, Object> reportApplicationViewMap = reportFascadeSerivce.getReportData(rROb);
//
//		@SuppressWarnings("unchecked")
//		List<ServiceProviderReportView> reportApplicationViewList = (List<ServiceProviderReportView>) reportApplicationViewMap.get("result");
//		DataList<ServiceProviderReportView> dataList = new DataList<ServiceProviderReportView>(reportApplicationViewList, (Long) reportApplicationViewMap.get("totalRecords"));
//		request.setAttribute("dataList", dataList);
		return "forward:/jasperReport/createReport";
	}
	
	public Map<String, Object> setupParametersMap(HttpServletRequest request) throws UnsupportedEncodingException {

		Map<String, Object> reportingParametersMap = new HashMap<String, Object>();

		String startDate = WebUtils.findParameterValue( request ,"startDate");
		if (startDate != null && startDate.length() > 0) {
			reportingParametersMap.put("startDate", URLDecoder.decode(startDate, "UTF-8"));
		}

		String endDate =  WebUtils.findParameterValue(request,"endDate");
		if (endDate != null && endDate.length() > 0) {
			reportingParametersMap.put("endDate", URLDecoder.decode(endDate, "UTF-8"));
		}
		
		String serviceName = WebUtils.findParameterValue( request ,"serviceName");
		if (serviceName != null && serviceName.length() > 0) {
			reportingParametersMap.put("serviceName", URLDecoder.decode(serviceName, "UTF-8"));
		}

		String serviceProviderId = WebUtils.findParameterValue( request ,"serviceProviderId");
		if (serviceProviderId != null && serviceProviderId.length() > 0) {
			reportingParametersMap.put("serviceProviderId", URLDecoder.decode(serviceProviderId, "UTF-8"));
		}

		String numberOfRecords = WebUtils.findParameterValue( request ,"numberOfRecords");
		if (numberOfRecords != null && numberOfRecords.length() > 0) {
			reportingParametersMap.put("numberOfRecords", URLDecoder.decode(numberOfRecords, "UTF-8"));
		}
		return reportingParametersMap;
	}
	
	@RequestMapping(value = "/export")
	public String export(Model model, @RequestParam(value="reportName") String reportName,@RequestParam(value="format")String format,HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, Object> reportingParametersMap=setupParametersMap(request);
		request.setAttribute("reportingParametersMap", reportingParametersMap);
		return "forward:/jasperReport/export";
	}
}
