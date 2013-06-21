package com.zuni.serviceprovider.service;

import java.util.List;
import java.util.Map;

import com.zuni.serviceprovider.domain.ServiceProvider;
import com.zuni.serviceprovider.domain.ServiceProviderTracking;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;
/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface ServiceProviderService {
	/**
	 * service provider crud operations API
	 * @param serviceProvider
	 * @return
	 */
	ServiceProvider createServiceProvider(ServiceProvider serviceProvider) throws BusinessException;
	void updateServiceProvider(Long servicePrviderId, ServiceProvider serviceProvider) throws BusinessException;
	void deleteServiceProvider(Long serviceProviderId) throws BusinessException;
	ServiceProvider getActiveServiceProviderById(Long serviceProvider);
	void createServiceProviderHitTracking(Long serviceProviderId, ServiceProviderTracking serviceProviderTracking) throws BusinessException;
	
	/**
	 * service provider finder APIs
	 */
	List<ServiceProvider> findAllActiveServiceProvider(Integer pageNumber ,Integer pageSize , String sortColumnName , String order) throws BusinessException;
	Map<String, Object> findAllActiveServiceProviderMap(Integer pageNumber ,Integer pageSize , String sortColumnName , String order, String string, Object sarchValue) throws BusinessException;
	List<ServiceProvider> findAllServiceProviderByAreaName(String AreaName , Integer startIndex ,Integer numberOfElement , String sortColumnName , String order) throws BusinessException;
	List<ServiceProvider> findAllServiceProviderByInstructorName(String instructorName) throws BusinessException;
	
	Map<String, Object> findMostFavorableServiceProvider(ReportRequestObject rROb) throws BusinessException;
	Map<String, Object> findLeastFavorableServiceProvider(ReportRequestObject rROb) throws BusinessException;
	byte[] getServiceProviderImage(Long id);
	
}
