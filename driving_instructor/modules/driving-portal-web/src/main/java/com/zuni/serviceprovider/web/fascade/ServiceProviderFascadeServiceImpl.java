package com.zuni.serviceprovider.web.fascade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zuni.serviceprovider.domain.ServiceArea;
import com.zuni.serviceprovider.domain.ServiceProvider;
import com.zuni.serviceprovider.domain.ServiceProviderForm;
import com.zuni.serviceprovider.domain.ServiceProviderListForm;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.exception.ErrorMessage;
import com.zuni.serviceprovider.service.ServiceAreaService;
import com.zuni.serviceprovider.service.ServiceProviderService;
import com.zuni.serviceprovider.utils.DataTableUtils;

/**
 * 
 * @author Zuned Ahmed
 *
 */
@Service
public class ServiceProviderFascadeServiceImpl extends ServiceFascadeHelper implements ServiceProviderFascadeService {

	@Autowired
	private ServiceProviderService serviceProviderService;
	
	@Autowired
	private ServiceAreaService serviceAreaService;
	
	@Transactional(readOnly=true)
	@Override
	public List<ServiceProviderListForm> findAllActiveInstuctor(String pageNumber, String pageSize, String sortColumnNumber, String order) throws BusinessException {
		Integer page = null, ps = null, sc = null;
		if (pageNumber != null && !pageNumber.trim().isEmpty() && pageSize != null && !pageSize.trim().isEmpty()) {
			page = Integer.parseInt(pageNumber.trim());
			ps = Integer.parseInt(pageSize.trim());
		}
		List<ServiceProvider> list = this.serviceProviderService.findAllActiveServiceProvider(page, ps, super.getColumnName(sc), order);
		return super.getServiceProviderListForm(list);
	}
	
	@Override
	public void createServiceProviderProvider( ServiceProviderForm serviceProviderForm) throws BusinessException {
		ServiceProvider sp = super.getServiceProviderOnCreate(serviceProviderForm);
		this.serviceProviderService.createServiceProvider(sp);
	}
	
	@Transactional(readOnly=true)
	@Override
	public ServiceProviderForm getServiceProviderById(Long serviceProvider) throws BusinessException {
		ServiceProvider sp = serviceProviderService.getActiveServiceProviderById(serviceProvider);
		if(sp == null){
			throw new BusinessException(new ErrorMessage("error.InvalidServiceProviderId" ,"Invalid Service Provider Id." ) , null);
		}
		return super.getServiceProviderForm(sp);
	}
	
	@Override
	public void updateServiceProvider(Long servicePrviderId, ServiceProviderForm serviceProviderForm) throws BusinessException {
		ServiceProvider serviceProvider = super.getServiceProviderOnUpdate(serviceProviderForm);
		this.serviceProviderService.updateServiceProvider(servicePrviderId , serviceProvider);
	}
	
	@Transactional(readOnly=true)
	@Override
	public byte[] getServiceProviderImage(Long id) {
		return this.serviceProviderService.getServiceProviderImage(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> findAllActiveInstuctor( Map<String, Object> queryParams) throws BusinessException {
		Integer page = DataTableUtils.getPageNumber(queryParams),
				ps = DataTableUtils.getPageLength(queryParams), 
				sc = DataTableUtils.getSortColumn(queryParams)  ;
		String order=DataTableUtils.getSortOrder(queryParams) , searchBy = DataTableUtils.getSearchByColumn(queryParams)  ;
		Object  sarchValue = DataTableUtils.getSearchByColumnVaue(queryParams);
		Map<String, Object> map = this.serviceProviderService.findAllActiveServiceProviderMap(page, ps, super.getColumnName(sc), order ,searchBy,sarchValue );
		@SuppressWarnings("unchecked")
		List<ServiceProviderListForm> result = super.getServiceProviderListForm(( List<ServiceProvider> )map.get("result"));
		map.put("result", result);
		return map;
	}
	
	@Override
	public Boolean deleteServiceProviderById(Long serviceProviderId) throws BusinessException {
		 this.serviceProviderService.deleteServiceProvider(serviceProviderId);
		 return Boolean.TRUE;
	}
	
	@Override
	public List<ServiceArea> getAllServiceArea() {
		return serviceAreaService.getAllServiceActive();
	}
}
