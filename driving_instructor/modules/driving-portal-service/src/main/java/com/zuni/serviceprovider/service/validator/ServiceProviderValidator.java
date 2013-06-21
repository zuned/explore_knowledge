package com.zuni.serviceprovider.service.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zuni.serviceprovider.constant.ConstantUtil;
import com.zuni.serviceprovider.constant.ConstantUtil.STATUS;
import com.zuni.serviceprovider.domain.Packages;
import com.zuni.serviceprovider.domain.ServiceArea;
import com.zuni.serviceprovider.domain.ServiceProvider;
import com.zuni.serviceprovider.domain.Services;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.exception.ErrorMessage;
import com.zuni.serviceprovider.repository.PackageRepository;
import com.zuni.serviceprovider.repository.ServicesRepository;
import com.zuni.serviceprovider.service.ServiceAreaService;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public abstract class ServiceProviderValidator {
	
	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ServicesRepository servicesRepository;
	
	@Autowired
	private ConstantUtil constantUtil;
	
	@Autowired
	private PackageRepository packageRepository;
	
	@Autowired
	private ServiceAreaService serviceAreaService;
	
	protected void validateServiceProviderBeforeCreation( ServiceProvider serviceProvider) throws BusinessException {
		logger.debug("+validateServiceProviderBeforeCreation+");
		validateService(serviceProvider , serviceProvider.getService());
		validateAndUpdatePackageEnrolledByMembershipType(serviceProvider , serviceProvider.getMemberType() );
		validateServiceArea(serviceProvider , serviceProvider.getServiceAreas());
		logger.debug("-validateServiceProviderBeforeCreation-");
	}

	private void validateServiceArea(ServiceProvider serviceProvider, Set<ServiceArea> serviceAreas) throws BusinessException {
		if(serviceAreas == null || serviceAreas.size() == 0) {
			logger.warn("No service Area Assigned.");
			return;
		}
		Set<String> saName = new HashSet<String>();
		for(ServiceArea sa :  serviceAreas) {
			saName.add(sa.getAreaName());
		}
		List<ServiceArea> serviceAreasFromDB = serviceAreaService.getAllServiceActive(saName);
		if(serviceAreasFromDB.size() != saName.size() ) {
			logger.warn("Invalid Service Area - ",saName);
			throw new BusinessException(new ErrorMessage("invalid.serviceAreaName" ,"Please provide Valid Service Area Name." ) ,null,serviceAreas.toString());
		}
		
		serviceProvider.getServiceAreas().clear();
		serviceProvider.getServiceAreas().addAll(serviceAreasFromDB);
	}

	private void validateAndUpdatePackageEnrolledByMembershipType( ServiceProvider serviceProvider, String memberType) throws BusinessException {
		if( memberType == null || memberType.trim().isEmpty()) {
			logger.warn("Member Type cannot be empty.",memberType);
			throw new BusinessException(new ErrorMessage("invalid.memberType","Please provide valid Member Type"), null, memberType);
		}
		if( constantUtil.isFreeMemberShipType(memberType) ) {
			serviceProvider.setPackageEnrolled(null);
		} else if ( serviceProvider.getPackageEnrolled() == null || serviceProvider.getPackageEnrolled().getPackages() == null || serviceProvider.getPackageEnrolled().getPackages().getName() == null ) {
			logger.warn("package name is not provided.Please select valid package");
			throw new BusinessException(new ErrorMessage("invalid.package","Please provide valid Package."), null);
		} else {
			Packages packages = packageRepository.findByNameIgnoreCaseAndStatus(serviceProvider.getPackageEnrolled().getPackages().getName() , STATUS.ACTIVE );
			if(packages == null) {
				logger.warn("unable to find the valid package with name {} "  , serviceProvider.getPackageEnrolled().getPackages().getName());
				throw new BusinessException(new ErrorMessage("invalid.package","Please provide valid Package."), null, serviceProvider.getPackageEnrolled().getPackages().getName());
			}
			serviceProvider.getPackageEnrolled().setPackages(packages);
		}
	}

	private void validateService(ServiceProvider serviceProvider , Services service) throws BusinessException {
		if( service.getName() == null) {
			logger.warn("Service Name {} is invalid"  ,service.getName() );
			throw new BusinessException(new ErrorMessage("invalid.serviceName","Please provide valid Service Name"), null, service.getName());
		}
		Services s = servicesRepository.findByNameIgnoreCase(service.getName());
		if(s== null){
			logger.warn("Unable to find Service Name {} is invalid"  ,service.getName() );
			throw new BusinessException(new ErrorMessage("invalid.serviceName","Please provide valid Service Name"), null, service.getName());
		}
		serviceProvider.setService(s);
	}

	protected void validateServiceProviderBeforUpdation( ServiceProvider serviceProvider) throws BusinessException {
		validateService(serviceProvider , serviceProvider.getService());
		validateAndUpdatePackageEnrolledByMembershipType(serviceProvider , serviceProvider.getMemberType() );
		validateServiceArea(serviceProvider , serviceProvider.getServiceAreas());
	}

}
