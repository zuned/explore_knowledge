package com.zuni.serviceprovider.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.zuni.serviceprovider.domain.SeriviceAreaTracking;
import com.zuni.serviceprovider.domain.ServiceArea;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface ServiceAreaService {

	void createServiceArea(ServiceArea serviceArea) throws BusinessException;
	
	Map<String, Object> findLeastFavorableServiceArea(ReportRequestObject rROb) throws BusinessException;
	
	Map<String, Object> findMostFavorableServiceArea(ReportRequestObject rROb) throws BusinessException;
	
	void createServiceAreaHitTracking(SeriviceAreaTracking sriviceAreaTracking) throws BusinessException;

	List<ServiceArea> getAllServiceActive();

	List<ServiceArea> getAllServiceActive(Collection<String> serviceAears);

}
