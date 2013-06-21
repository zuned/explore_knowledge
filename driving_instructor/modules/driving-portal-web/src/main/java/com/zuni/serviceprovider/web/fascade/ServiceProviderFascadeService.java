package com.zuni.serviceprovider.web.fascade;

import java.util.List;
import java.util.Map;

import com.zuni.serviceprovider.domain.ServiceArea;
import com.zuni.serviceprovider.domain.ServiceProviderForm;
import com.zuni.serviceprovider.domain.ServiceProviderListForm;
import com.zuni.serviceprovider.exception.BusinessException;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface ServiceProviderFascadeService {

	List<ServiceProviderListForm> findAllActiveInstuctor(String pageNumber,
			String pageSize, String sortColumnNumber, String order)
			throws BusinessException;

	void createServiceProviderProvider(ServiceProviderForm serviceProviderForm)
			throws BusinessException;

	ServiceProviderForm getServiceProviderById(Long id) throws BusinessException;

	void updateServiceProvider(Long servicePrviderId, ServiceProviderForm serviceProviderForm) throws BusinessException;

	byte[] getServiceProviderImage(Long id);

	Map<String, Object> findAllActiveInstuctor( Map<String, Object> queryParams) throws BusinessException;

	Boolean deleteServiceProviderById(Long id) throws BusinessException;

	List<ServiceArea> getAllServiceArea();

}
