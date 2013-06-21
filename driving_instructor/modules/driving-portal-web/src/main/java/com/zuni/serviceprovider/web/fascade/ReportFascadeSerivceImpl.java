package com.zuni.serviceprovider.web.fascade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuni.serviceprovider.domain.ReportForm;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;
import com.zuni.serviceprovider.service.ServiceAreaService;
import com.zuni.serviceprovider.service.ServiceProviderService;

/**
 * 
 * @author Zuned Ahmed
 *
 */
@Service
public class ReportFascadeSerivceImpl implements ReportFascadeSerivce {

	@Autowired
	private ServiceProviderService serviceProviderService;
	
	private ServiceAreaService serviceAreaService;
	
	public static final String LEAST_POPULAR_SERVICE_PROVIDER = "least_popular_service_provider";
	public static final String MOST_POPULAR_SERVICE_PROVIDER = "most_popular_service_provider";
	public static final String LEAST_POPULAR_SERVICE_AREA = "least_popular_service_area";
	public static final String MOST_POPULAR_SERVICE_AREA= "most_popular_service_area";
	
	@Override
	public List<ReportForm> findReportByName(String reportName, String pageNumber, String pageSize, String sortColumnNumber,	String order) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> getReportData(ReportRequestObject rROb) throws BusinessException {
		Map<String, Object> resultMap = null;
		if(rROb.getReportName() != null ) {
			if(rROb.getReportName().equalsIgnoreCase(LEAST_POPULAR_SERVICE_PROVIDER)){
				resultMap = this.serviceProviderService.findLeastFavorableServiceProvider(rROb);
			} else if(rROb.getReportName().equalsIgnoreCase(MOST_POPULAR_SERVICE_PROVIDER)) {
				resultMap = this.serviceProviderService.findMostFavorableServiceProvider(rROb);
			} else if(rROb.getReportName().equalsIgnoreCase(LEAST_POPULAR_SERVICE_AREA)) {
				resultMap = this.serviceAreaService.findLeastFavorableServiceArea(rROb);
			} else if(rROb.getReportName().equalsIgnoreCase(MOST_POPULAR_SERVICE_AREA)) {
				resultMap = this.serviceAreaService.findMostFavorableServiceArea(rROb);
			}
		}
		return resultMap;
	}

}
