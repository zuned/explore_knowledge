package com.zuni.serviceprovider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zuni.serviceprovider.constant.ConstantUtil.STATUS;
import com.zuni.serviceprovider.domain.Address;
import com.zuni.serviceprovider.domain.PackageEnrolled;
import com.zuni.serviceprovider.domain.ServiceArea;
import com.zuni.serviceprovider.domain.ServiceExtraInfo;
import com.zuni.serviceprovider.domain.ServiceProvider;
import com.zuni.serviceprovider.domain.ServiceProviderOtherDetails;
import com.zuni.serviceprovider.domain.ServiceProviderServiceDetail;
import com.zuni.serviceprovider.domain.ServiceProviderTracking;
import com.zuni.serviceprovider.domain.SpecialOffer;
import com.zuni.serviceprovider.domain.Testimonial;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.exception.ErrorMessage;
import com.zuni.serviceprovider.report.domain.PopularServiceProivderReport;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;
import com.zuni.serviceprovider.repository.ServiceProviderRepository;
import com.zuni.serviceprovider.repository.ServiceProviderRepositoryCustom;
import com.zuni.serviceprovider.repository.ServiceProviderTrackingRepository;
import com.zuni.serviceprovider.service.ServiceProviderService;
import com.zuni.serviceprovider.service.validator.ServiceProviderValidator;

/**
 * 
 * @author Zuned Ahmed
 *
 */
@Transactional(readOnly=true)
@Service
public class ServiceProviderServiceImpl extends ServiceProviderValidator implements ServiceProviderService {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	private ServiceProviderTrackingRepository serviceProviderTrackingRepository;
	
	@Autowired
	private ServiceProviderRepositoryCustom serviceProviderRepositoryCustom;
	
	private BusinessException INVALIDID = new BusinessException(new ErrorMessage("invalid.serviceProviderId","Please provide valid Service Provider"), null);
	
	
	@Transactional(readOnly=false)
	@Override
	public ServiceProvider createServiceProvider( ServiceProvider serviceProvider) throws BusinessException {
		super.validateServiceProviderBeforeCreation(serviceProvider );
		prepareServiceProvider(serviceProvider);
		return this.serviceProviderRepository.save(serviceProvider);
	}

	@Transactional(readOnly=false)
	@Override
	public void updateServiceProvider(Long serviceProviderId ,ServiceProvider serviceProvider) throws BusinessException {
		ServiceProvider serviceProviderDb = getActiveServiceProviderById(serviceProviderId);
		if(serviceProviderDb == null) {
			throw INVALIDID;
		}
		super.validateServiceProviderBeforUpdation(serviceProvider);
		prepareServiceProvider(serviceProviderDb ,serviceProvider);
	}

	@Transactional(readOnly=false)
	@Override
	public void deleteServiceProvider(Long serviceProviderId) throws BusinessException {
		ServiceProvider serviceProviderDb = getActiveServiceProviderById(serviceProviderId);
		if(serviceProviderDb == null) {
			throw INVALIDID;
		}
		serviceProviderDb.setStatus(STATUS.INACTIVE);
	}

	@Override
	public ServiceProvider getActiveServiceProviderById(Long serviceProviderId) {
		return this.serviceProviderRepository.findByIdAndStatus(serviceProviderId , STATUS.ACTIVE);
	}

	@Transactional(readOnly=false)
	@Override
	public void createServiceProviderHitTracking(Long serviceProviderId, ServiceProviderTracking serviceProviderTracking) throws BusinessException {
		ServiceProvider serviceProviderDb = getActiveServiceProviderById(serviceProviderId);
		if(serviceProviderDb == null) {
			throw INVALIDID;
		}
		serviceProviderTracking.setServiceProvider(serviceProviderDb);
		this.serviceProviderTrackingRepository.save(serviceProviderTracking);
	}

	@Override
	public List<ServiceProvider> findAllActiveServiceProvider(Integer pageNumber, Integer pageSize, String sortColumnName, String ascAndDesc) {
		Sort sort = getSortOrderObject(sortColumnName , ascAndDesc );
		Pageable pagenable = getPagenableRequestObject(pageNumber , pageSize , sort);
		if(pagenable == null && sort != null) {
			return serviceProviderRepository.findByStatus(STATUS.ACTIVE ,sort);
		}
		Page<ServiceProvider> serviceProviderList =  serviceProviderRepository.findByStatus(STATUS.ACTIVE ,pagenable );
		return serviceProviderList.getContent();
	}

	@Override
	public Map<String, Object> findAllActiveServiceProviderMap( Integer pageNumber, Integer pageSize, String sortColumnName, String ascAndDesc, String searchBy , Object searchValue ) throws BusinessException {
		Map<String, Object> map = new HashMap<String,Object>();
		
		Sort sort = getSortOrderObject(sortColumnName , ascAndDesc );
		Pageable pagenable = getPagenableRequestObject(pageNumber , pageSize , sort);
		if(pagenable == null && sort != null) {
			List<ServiceProvider>  spList =  getServiceProviderList(sort , searchBy , searchValue ) ; 
			map.put("result", spList);
			map.put("totalRecords", spList!=null ? spList.size() : 0);
			return map;
		}
		Page<ServiceProvider> serviceProviderList =  getServiceProviderList(pagenable, searchBy , searchValue);
		map.put("result", serviceProviderList.getContent());
		map.put("totalRecords", serviceProviderList.getTotalElements());
		return map;
	}
	

	private Page<ServiceProvider> getServiceProviderList(Pageable pagenable, String searchBy, Object searchValue) {
		Page<ServiceProvider> providers = null;
		if ( searchBy !=null && searchValue!=null ) {
			if ( "sac".equalsIgnoreCase(searchBy) ) {//
				String []postCodes = ((String)searchValue).split(",");
//				providers = serviceProviderRepository.findByStatusAndServiceArea_PostCodeIn(STATUS.ACTIVE ,postCodes , pagenable );
			} else if ( "name".equalsIgnoreCase(searchBy) ) {
				String []name = ((String)searchValue).split(" ");
				if ( name.length==1 ) {
					providers = serviceProviderRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingAndStatus(name[0], name[0] , STATUS.ACTIVE ,pagenable );
				} else if ( name.length == 2) {
					providers = serviceProviderRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndStatus(name[0], name[1] , STATUS.ACTIVE ,pagenable );
				} else if( name.length == 3) {
					providers = serviceProviderRepository.findByFirstNameIgnoreCaseAndMiddleNameIgnoreCaseAndLastNameIgnoreCaseAndStatus(name[0], name[1] , name[2],STATUS.ACTIVE ,pagenable );
				}
			}
		} else {
			providers = serviceProviderRepository.findByStatus(STATUS.ACTIVE ,pagenable );
		}
		return providers;
	}

	private List<ServiceProvider> getServiceProviderList(Sort sort, String searchBy, Object searchValue) {
		List<ServiceProvider> providers = null;
		if ( searchBy !=null && searchValue!=null ) {
			if ( "sac".equalsIgnoreCase(searchBy) ) {//
				String []postCodes = ((String)searchValue).split(",");
//				providers = serviceProviderRepository.findByStatusAndServiceArea_PostCodeIn(STATUS.ACTIVE ,postCodes , sort );
			} else if ( "name".equalsIgnoreCase(searchBy) ) {
				String []name = ((String)searchValue).split(" ");
				if ( name.length==1 ) {
					providers = serviceProviderRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingAndStatus(name[0], name[0] , STATUS.ACTIVE ,sort );
				} else if ( name.length == 2) {
					providers = serviceProviderRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndStatus(name[0], name[1] , STATUS.ACTIVE ,sort );
				} else if( name.length == 3) {
					providers = serviceProviderRepository.findByFirstNameIgnoreCaseAndMiddleNameIgnoreCaseAndLastNameIgnoreCaseAndStatus(name[0], name[1] , name[2],STATUS.ACTIVE ,sort );
				}
			}
		} else {
			providers = serviceProviderRepository.findByStatus(STATUS.ACTIVE ,sort);
		}
		return providers;
	}

	@Override
	public List<ServiceProvider> findAllServiceProviderByAreaName(String AreaName, Integer pageNumber, Integer pageSize, String sortColumnName, String ascAndDesc) {
		Sort sort = getSortOrderObject(sortColumnName , ascAndDesc );
		Pageable pagenable = getPagenableRequestObject(pageNumber , pageSize , sort);
		if(pagenable == null && sort != null) {
			return serviceProviderRepository.findByStatus(STATUS.ACTIVE ,sort);
		}
		Page<ServiceProvider> serviceProviderList =  serviceProviderRepository.findByStatus(STATUS.ACTIVE ,pagenable );
		return serviceProviderList.getContent();
	}

	@Override
	public List<ServiceProvider> findAllServiceProviderByInstructorName( String serviceProviderName) {
		String []name =  serviceProviderName.split(" ");
		return serviceProviderRepository.findByStatusAndFirstNameOrLastName(STATUS.ACTIVE ,name[0] ,name.length > 1 ? name[1] : name[0] );
	}
	
	@Override
	public Map<String, Object> findLeastFavorableServiceProvider( ReportRequestObject rROb) throws BusinessException {
		 Map<String, Object> resultMap = new HashMap<String, Object>();
		 
		List<ServiceProviderTracking> list = serviceProviderRepositoryCustom.findLeastFavorableServiceProvider( rROb) ;
		List<PopularServiceProivderReport> pspRlist = getPopularServiceProivderReport(list);
		
		resultMap.put("result", pspRlist);
		resultMap.put("totalRecords", pspRlist.size());
		
		return resultMap;
	}
	
	@Override
	public byte[] getServiceProviderImage(Long serviceProviderId) {
		ServiceProvider sp = this.serviceProviderRepository.findByIdAndStatus(serviceProviderId, STATUS.ACTIVE);
		return sp.getServiceProviderOtherDetails() != null ?sp.getServiceProviderOtherDetails().getImage() : null;
	}
	
	private List<PopularServiceProivderReport> getPopularServiceProivderReport( List<ServiceProviderTracking> list) {
		List<PopularServiceProivderReport> pspRlist = new ArrayList<PopularServiceProivderReport>();
		if(list!=null) {
			for(ServiceProviderTracking spt : list){
				PopularServiceProivderReport spR = new PopularServiceProivderReport();
				spR.setServiceProviderName(spt.getServiceProvider().getFirstName()+" "+spt.getServiceProvider().getLastName());
				spR.setServiceProviderId(spt.getServiceProvider().getId());
				//spR.setNumberOfHits(numberOfHits);
			}
		}
		return pspRlist;
	}

	@Override
	public Map<String, Object> findMostFavorableServiceProvider( ReportRequestObject rROb) throws BusinessException {
		 Map<String, Object> resultMap = new HashMap<String, Object>();
		 
		List<ServiceProviderTracking> list = serviceProviderRepositoryCustom.findMostFavorableServiceProvider( rROb);
		List<PopularServiceProivderReport> pspRlist = getPopularServiceProivderReport(list);
		
		resultMap.put("result", pspRlist);
		resultMap.put("totalRecords", pspRlist.size());
			
		return resultMap;
	}

	private Pageable getPagenableRequestObject(Integer pageNumber, Integer pageSize, Sort sort) {
		Pageable pagenable = null;
		if(pageNumber != null && pageSize!=null){
			pagenable = new PageRequest(pageNumber, pageSize , sort);
		}
		return pagenable;
	}

	private Sort getSortOrderObject(String sortColumnName, String ascAndDesc) {
		Sort sort = null;
		if(sortColumnName!=null){
			Direction direction = "DESC".equalsIgnoreCase(ascAndDesc)? Direction.DESC : Direction.ASC;
			Order order = new Order(direction ,sortColumnName);
			sort = new Sort(order);
		}
		return sort;
	}

	
	private void prepareServiceProvider(ServiceProvider serviceProvider) {
		logger.debug("+prepareServiceProvider+");
		updateServiceProviderServiceArea(serviceProvider , serviceProvider.getServiceAreas());
		updateServiceProviderOtherDetails(serviceProvider , serviceProvider.getServiceProviderOtherDetails());
		updateServiceProviderServiceDetail(serviceProvider , serviceProvider.getServiceProviderServiceDetail());
		updateAddresses(serviceProvider , serviceProvider.getAddresses());
		updatePackageEnrolled(serviceProvider , serviceProvider.getPackageEnrolled());
		updateSpecialOffers(serviceProvider , serviceProvider.getSpecialOffers());
		logger.debug("-prepareServiceProvider-");
	}
	
	private void prepareServiceProvider(ServiceProvider serviceProviderDb, ServiceProvider newServiceProvider) {
		// TODO Auto-generated method stub
		logger.debug("+prepareServiceProvider on Update +");
		updateServiceProviderServiceAreaOnUpdate(serviceProviderDb , newServiceProvider.getServiceAreas() );
		updateServiceProviderOtherDetailsOnUpdate(serviceProviderDb , newServiceProvider.getServiceProviderOtherDetails());
		
		updateServiceProviderServiceDetailOnUpdate(serviceProviderDb , newServiceProvider.getServiceProviderServiceDetail());
		
		updatePackageEnrolledOnUpdate(serviceProviderDb , newServiceProvider.getPackageEnrolled());
		
		updateAddressesOnUpdate(serviceProviderDb , newServiceProvider.getAddresses());
		
		updateSpecialOffersOnUpdate(serviceProviderDb , newServiceProvider.getSpecialOffers());
		
		logger.debug("-prepareServiceProvider on Update -");
	}
		
	private void updateSpecialOffersOnUpdate(ServiceProvider serviceProviderDb,
			Set<SpecialOffer> specialOffers) {
		if(serviceProviderDb.getSpecialOffers() == null || serviceProviderDb.getSpecialOffers().size() == 0 ) {
			updateSpecialOffers(serviceProviderDb , specialOffers);
			 serviceProviderDb.setSpecialOffers(specialOffers);
		} else  if(specialOffers == null || specialOffers.size() == 0) {
			serviceProviderDb.getSpecialOffers().clear();
		} else {
			Set<SpecialOffer> addToSave = new HashSet<SpecialOffer>();
			for(SpecialOffer sp : specialOffers) {
				boolean found = false;
				for(SpecialOffer spDb : serviceProviderDb.getSpecialOffers()){
					if(spDb.getId().equals(sp.getId()) || spDb.getName().equals(sp.getName())) {
						spDb.setName(sp.getName());
						spDb.setDescription(sp.getDescription());
						spDb.setStartDate(sp.getStartDate());
						spDb.setEndDate(sp.getEndDate());
						spDb.setStatus(sp.getStatus());
						found = true;
						addToSave.add(spDb);
						break;
					}
				}
				if(!found){
					sp.setServiceProvider(serviceProviderDb);
					addToSave.add(sp);
				}
			}
			serviceProviderDb.getSpecialOffers().clear();
			serviceProviderDb.getSpecialOffers().addAll(addToSave);
		}
		
	}

	private void updateAddressesOnUpdate(ServiceProvider serviceProviderDb,
			Set<Address> addresses) {
		if(serviceProviderDb.getAddresses() == null || serviceProviderDb.getAddresses().size() == 0 ) {
			 updateAddresses(serviceProviderDb , addresses);
			 serviceProviderDb.setAddresses(addresses);
		} else {
			Set<Address> addToSave = new HashSet<Address>();
			for(Address a : addresses) {
				boolean found = false;
				if(a.getId() == null) {
					for(Address aDb : serviceProviderDb.getAddresses()){
						if(aDb.getId().equals(a.getId())) {
							a.setServiceProvider(serviceProviderDb);
							addToSave.add(a);
							found = true;
							break;
						}
					}
				}
				if(!found){
					a.setServiceProvider(serviceProviderDb);
					addToSave.add(a);
				}
			}
			serviceProviderDb.getAddresses().clear();
			serviceProviderDb.getAddresses().addAll(addToSave);
		}
	}

	private void updatePackageEnrolledOnUpdate( ServiceProvider serviceProviderDb, PackageEnrolled packageEnrolled) {
		 if( serviceProviderDb.getPackageEnrolled() == null ) {
			 updatePackageEnrolled(serviceProviderDb, packageEnrolled);
			 serviceProviderDb.setPackageEnrolled(packageEnrolled);
		 } else if(packageEnrolled == null) {
			logger.error("packageEnrolled enrolled cannot be null");
		 } else {
			 serviceProviderDb.getPackageEnrolled().setStartDate(packageEnrolled.getStartDate());
			 serviceProviderDb.getPackageEnrolled().setStatus(packageEnrolled.getStatus());
		 }
		
	}

	private void updateServiceProviderServiceDetailOnUpdate( ServiceProvider serviceProviderDb, ServiceProviderServiceDetail serviceProviderServiceDetail) {
		if(serviceProviderDb.getServiceProviderServiceDetail() == null  ) {
			updateServiceProviderServiceDetail(serviceProviderDb , serviceProviderServiceDetail);
			serviceProviderDb.setServiceProviderServiceDetail(serviceProviderServiceDetail);
		} else {
			serviceProviderDb.getServiceProviderServiceDetail().setQualification(serviceProviderServiceDetail.getQualification());
			serviceProviderDb.getServiceProviderServiceDetail().setServiceStartDate(serviceProviderServiceDetail.getServiceStartDate());
			serviceProviderDb.getServiceProviderServiceDetail().setSpeciality(serviceProviderServiceDetail.getSpeciality());
			serviceProviderDb.getServiceProviderServiceDetail().setTiming(serviceProviderServiceDetail.getTiming());
			updateServiceProviderServiceDetailExtraInfoOnUpdate(serviceProviderDb.getServiceProviderServiceDetail() , serviceProviderServiceDetail.getServiceExtraInfo());
		}
		
	}

	private void updateServiceProviderServiceDetailExtraInfoOnUpdate( ServiceProviderServiceDetail serviceProviderServiceDetail,
			Set<ServiceExtraInfo> serviceExtraInfo) {
		if( serviceProviderServiceDetail.getServiceExtraInfo() == null || serviceProviderServiceDetail.getServiceExtraInfo().size() == 0){
			updateServiceExtraInfo(serviceProviderServiceDetail, serviceExtraInfo);
		} else if(serviceExtraInfo == null || serviceExtraInfo.isEmpty()) {
			serviceProviderServiceDetail.getServiceExtraInfo().clear();
		} else {
			Set<ServiceExtraInfo> foundList = new HashSet<ServiceExtraInfo>();
			for (ServiceExtraInfo sei : serviceExtraInfo) {
				boolean found = false;
				for (ServiceExtraInfo seiDB : serviceProviderServiceDetail.getServiceExtraInfo()) {
					if(seiDB.getProperty().equalsIgnoreCase(sei.getProperty())) {
						seiDB.setValue(sei.getValue());
						foundList.add(seiDB);
						found = true;break;
					}
				}
				if(!found) {//NEW PROPERTY
					foundList.add(sei);
					sei.setServiceProviderServiceDetail(serviceProviderServiceDetail);
				}
			}
			serviceProviderServiceDetail.getServiceExtraInfo().clear();
			serviceProviderServiceDetail.getServiceExtraInfo().addAll(foundList);
			
		}
		
	}

	private void updateServiceProviderServiceAreaOnUpdate( ServiceProvider serviceProviderDb, Set<ServiceArea> serviceAreas) {
		if(serviceProviderDb.getServiceAreas() == null || serviceProviderDb.getServiceAreas().size() == 0 ) {
			updateServiceProviderServiceArea(serviceProviderDb , serviceAreas);
			serviceProviderDb.setServiceAreas(serviceAreas);
		} else if(serviceAreas == null || serviceAreas.size() == 0) {
			serviceProviderDb.getServiceAreas().clear();
		} else {
			for(ServiceArea sa : serviceAreas) {
				if ( !serviceProviderDb.getServiceAreas().contains(sa) ) {
					serviceProviderDb.getServiceAreas().add(sa);
					sa.getServiceProviders().add(serviceProviderDb);
				}
			}
			Set<ServiceArea> notFoundList = new HashSet<ServiceArea>();
			for( ServiceArea saDB : serviceProviderDb.getServiceAreas() ) {
				if ( !serviceAreas.contains(saDB) ) {
					notFoundList.add(saDB);
				}
			}
			/** remove all service area which are not in new list**/
			serviceProviderDb.getServiceAreas().retainAll(serviceAreas);
			for( ServiceArea saDB : notFoundList ) {
				saDB.getServiceProviders().remove(serviceProviderDb);
			}
		}
		
	}
	
	private void updateServiceProviderOtherDetailsOnUpdate( ServiceProvider serviceProviderDb,
			ServiceProviderOtherDetails serviceProviderOtherDetails) {
		if(serviceProviderDb.getServiceProviderOtherDetails() == null  ) {
			updateServiceProviderOtherDetails(serviceProviderDb , serviceProviderOtherDetails);
			serviceProviderDb.setServiceProviderOtherDetails(serviceProviderOtherDetails);
		} else {
			serviceProviderDb.getServiceProviderOtherDetails().setAboutUs(serviceProviderOtherDetails.getAboutUs());
			if(serviceProviderOtherDetails.getImage() != null)
				serviceProviderDb.getServiceProviderOtherDetails().setImage(serviceProviderOtherDetails.getImage());
		}
	}

	private void updateServiceProviderServiceArea( ServiceProvider serviceProvider, Set<ServiceArea> serviceAreas) {
		if(serviceAreas!=null){
			for(ServiceArea sa : serviceAreas) {
				if( sa.getServiceProviders() == null ) {
					sa.setServiceProviders(new HashSet<ServiceProvider>());
				}
				sa.getServiceProviders().add(serviceProvider);
			}
		}
		
	}

	private void updateServiceProviderOtherDetails( ServiceProvider serviceProvider, ServiceProviderOtherDetails serviceProviderOtherDetails) {
		if(serviceProviderOtherDetails!=null){
			serviceProviderOtherDetails.setServiceProvider(serviceProvider);
		}
	}

	
	private void updateServiceProviderServiceDetail(ServiceProvider serviceProvider, ServiceProviderServiceDetail serviceProviderServiceDetail) {
		if(serviceProviderServiceDetail!=null) {
			serviceProviderServiceDetail.setServiceProvider(serviceProvider);
			updateServiceExtraInfo(serviceProvider.getServiceProviderServiceDetail() , serviceProviderServiceDetail.getServiceExtraInfo());
		}
		
	}
	
	private void updateServiceExtraInfo(ServiceProviderServiceDetail serviceProviderServiceDetail, Set<ServiceExtraInfo> serviceExtraInfo) {
		if(serviceExtraInfo!=null){
			for (ServiceExtraInfo sei : serviceExtraInfo) {
					sei.setServiceProviderServiceDetail(serviceProviderServiceDetail);
			}
		}
	}

	private void updateAddresses(ServiceProvider serviceProvider, Set<Address> addresses) {
		if(addresses!=null){
			for(Address address: addresses){
				address.setServiceProvider(serviceProvider);
			}
		}
	}

	private void updatePackageEnrolled(ServiceProvider serviceProvider, PackageEnrolled packageEnrolled) {
		if(packageEnrolled!=null) {
			packageEnrolled.setServiceProvider(serviceProvider);
		}
	}
	
	private void updateSpecialOffers(ServiceProvider serviceProvider, Set<SpecialOffer> specialOffers) {
		if(specialOffers!=null) {
			for(SpecialOffer specialOffer : specialOffers) {
				specialOffer.setServiceProvider(serviceProvider);
			}
		}
	}
	
	private void addTestimonials(ServiceProvider serviceProvider, Set<Testimonial> testimonials) {
		if(testimonials!=null) {
			for(Testimonial testimonial : testimonials) {
				testimonial.setServiceProvider(serviceProvider);
			}
		}
	}

}
