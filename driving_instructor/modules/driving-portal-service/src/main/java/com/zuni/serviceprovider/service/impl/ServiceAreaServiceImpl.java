package com.zuni.serviceprovider.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zuni.serviceprovider.domain.SeriviceAreaTracking;
import com.zuni.serviceprovider.domain.ServiceArea;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;
import com.zuni.serviceprovider.repository.ServiceAreaRepository;
import com.zuni.serviceprovider.repository.ServiceAreaTrackingRepository;
import com.zuni.serviceprovider.service.ServiceAreaService;

/**
 * 
 * @author Zuned Ahmed
 *
 */
@Transactional(readOnly=true)
@Service
public class ServiceAreaServiceImpl implements ServiceAreaService {
	
	@Autowired
	private ServiceAreaRepository serviceAreaRepository;
	
	@Autowired
	private ServiceAreaTrackingRepository serviceAreaTrackingRepository;

	@Transactional(readOnly=false)
	@Override
	public void createServiceArea(ServiceArea serviceArea) {
		this.serviceAreaRepository.save(serviceArea);
	}

	@Override
	public Map<String, Object> findLeastFavorableServiceArea( ReportRequestObject rROb) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> findMostFavorableServiceArea(ReportRequestObject rROb) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(readOnly=false)
	@Override
	public void createServiceAreaHitTracking( SeriviceAreaTracking sriviceAreaTracking) {
		this.serviceAreaTrackingRepository.save(sriviceAreaTracking);
	}

	@Override
	public List<ServiceArea> getAllServiceActive() {
		return this.serviceAreaRepository.findAll();
	}

	@Override
	public List<ServiceArea> getAllServiceActive(Collection<String> serviceAears) {
		return this.serviceAreaRepository.findByAreaNameIn(serviceAears);
	}
	
	

}
