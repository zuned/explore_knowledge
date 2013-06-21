package com.zuni.serviceprovider.web.fascade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.hibernate.HibernateUtil;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.zuni.serviceprovider.constant.ConstantUtil;
import com.zuni.serviceprovider.constant.ConstantUtil.STATUS;
import com.zuni.serviceprovider.domain.Address;
import com.zuni.serviceprovider.domain.ServiceArea;
import com.zuni.serviceprovider.domain.ServiceProvider;
import com.zuni.serviceprovider.domain.ServiceProviderForm;
import com.zuni.serviceprovider.domain.ServiceProviderListForm;
import com.zuni.serviceprovider.domain.ServiceProviderOtherDetails;
import com.zuni.serviceprovider.domain.Services;
import com.zuni.serviceprovider.domain.SpecialOffer;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.exception.BusinessRuntimeException;
import com.zuni.serviceprovider.exception.ErrorMessage;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public abstract class ServiceFascadeHelper {
	
	@Autowired
	private ConstantUtil constantUtil;
	
	protected List<ServiceProviderListForm> getServiceProviderListForm( List<ServiceProvider> list) {
		List<ServiceProviderListForm> result = new ArrayList<ServiceProviderListForm>();
		if(list!=null && list.size() > 0) {
			for(ServiceProvider sp : list){
				ServiceProviderListForm spf = new ServiceProviderListForm();
				spf.setName(sp.getFirstName() + " "+ sp.getLastName());
				spf.setId(sp.getId());
				spf.setMemberType(sp.getMemberType().toString());
				spf.setLicensce(sp.getLicenceNumber());
				spf.setDateOfRegistration(sp.getDateOfRegistration());
				spf.setContactPrefrence(sp.getContactPrefrence());
				if(sp.getServiceAreas() != null) {
					StringBuilder sac = new StringBuilder();
					Iterator<ServiceArea> itr = sp.getServiceAreas().iterator();
					int size = sp.getServiceAreas().size();
					for(int i = 1; itr.hasNext() ; i++) {
						ServiceArea sa = itr.next();
						if(i == size) {
							sac.append(sa.getPostCode());
						}else{
							sac.append(sa.getPostCode());
							sac.append(",");
						}
					}
					spf.setAreaCodes(sac.toString());
				}
				result.add(spf);
			}
		}
		return result;
	}

	protected String getColumnName(Integer sortColumn) {
		if(sortColumn != null){
			switch (sortColumn) {// FIXME
			case 1:
				return "id";
			case 2:
				return "firstName";
			case 3:
				return "memberType";
			case 4:
				return "dateOfRegistration";
			case 5:
				return "contactPrefrence";
			case 6:
				return "licenceNumber";
//			case 7:
//				return "serviceAreas.postCode";
			}
		}
		return null;
	}
	
	protected ServiceProvider getServiceProviderOnCreate( ServiceProviderForm serviceProviderForm) throws BusinessException {
		ServiceProvider sp =getServiceProvider(serviceProviderForm);
		sp.setDateOfRegistration(new Date());
		return sp;
	}

	protected ServiceProvider getServiceProvider(
			ServiceProviderForm serviceProviderForm) throws BusinessException {
		ServiceProvider sp = new ServiceProvider();
		sp.setFirstName(serviceProviderForm.getFirstName());
		sp.setMiddleName(serviceProviderForm.getMiddleName());
		sp.setLastName(serviceProviderForm.getLastName());
		if( constantUtil.isValidGender(serviceProviderForm.getGender())) 
			sp.setGender(serviceProviderForm.getGender());
		else{
			throw new BusinessException(new ErrorMessage("invald.gender" ,"Please provide correct gender" ) ,null,serviceProviderForm.getGender());
		}
		
		sp.setCompanyName(serviceProviderForm.getCompanyName());
		
		sp.setContactPrefrence(serviceProviderForm.getContactPrefrence());
		
		sp.setEmailId(serviceProviderForm.getEmailId());
		sp.setLicenceNumber(serviceProviderForm.getLicenceNumber());
		
		if( constantUtil.isValidMemberShipType(serviceProviderForm.getMemberType()) ) {
			sp.setMemberType(serviceProviderForm.getMemberType());
			sp.setPackageEnrolled(serviceProviderForm.getPackageEnrolled());
		}else {
			throw new BusinessException(new ErrorMessage("invalid.memberShip" ,"Please provide correct Membership Type." ) ,null,serviceProviderForm.getMemberType());
		}
		
		
		sp.setDateOfBirth(serviceProviderForm.getDateOfBirth());
		sp.setDateOfRegistration(serviceProviderForm.getDateOfRegistration());
		
		/** Adding Address */
		sp.setAddresses(new LinkedHashSet<Address>());
		sp.getAddresses().addAll(serviceProviderForm.getAddress());
		
		/** Adding Service */
		Services service = new Services();
		service.setName(serviceProviderForm.getServiceName());
		sp.setService(service);
		
		if ( serviceProviderForm.getSelectedServiceArea()!=null && serviceProviderForm.getSelectedServiceArea().size() > 0 ) {
			HashSet<ServiceArea> serviceAreas = new HashSet<ServiceArea>();
			for(String saName : serviceProviderForm.getSelectedServiceArea()) {
				ServiceArea sa = new ServiceArea();
				sa.setAreaName(saName);
				serviceAreas.add(sa);	
			}
			sp.setServiceAreas(serviceAreas);	
		}
		
		/** Service Provider other details */
		ServiceProviderOtherDetails serviceProviderOtherDetails = new ServiceProviderOtherDetails();
			serviceProviderOtherDetails.setAboutUs(serviceProviderForm.getAboutUs());
			if( serviceProviderForm.getPhoto()!=null && !serviceProviderForm.getPhoto().isEmpty() )
				try {
					serviceProviderOtherDetails.setImage(serviceProviderForm.getPhoto().getBytes());
				} catch (IOException e) {
					throw new BusinessRuntimeException(new ErrorMessage("error.photoread" ,"Internal Server Error" ) ,e);
				}
		sp.setServiceProviderOtherDetails(serviceProviderOtherDetails);
		
		sp.setServiceProviderServiceDetail(serviceProviderForm.getServiceProviderServiceDetail());
		
		/** Adding Special offer */
		if( serviceProviderForm.getSpecialOffers() != null ) {
			sp.setSpecialOffers( validateSpecialOffer(serviceProviderForm.getSpecialOffers()) );
		}
		sp.setStatus(STATUS.ACTIVE);
		return sp;
	}

	private Set<SpecialOffer> validateSpecialOffer( List<SpecialOffer> specialOffers) throws BusinessException {
		Set<SpecialOffer> spOff = new HashSet<SpecialOffer>();
		for(SpecialOffer spO : specialOffers) {
			if( spO.getStartDate().compareTo(spO.getEndDate())  >= 0 ) {
				throw new BusinessException(new ErrorMessage("error.offerDateInvalid" ,"Start Date should be less than end date" ) , null);
			}
			spOff.add(spO);
		}
		return spOff;
	}

	protected ServiceProviderForm getServiceProviderForm(ServiceProvider sp) {//todo
		ServiceProviderForm spf = new ServiceProviderForm();

		spf.setFirstName(sp.getFirstName());
		spf.setMiddleName(sp.getMiddleName());
		spf.setLastName(sp.getLastName());
		spf.setGender(sp.getGender());
		spf.setCompanyName(sp.getCompanyName());
		spf.setContactPrefrence(sp.getContactPrefrence());
		spf.setEmailId(sp.getEmailId());
		spf.setLicenceNumber(sp.getLicenceNumber());
		spf.setMemberType(sp.getMemberType());
		spf.setPackageEnrolled(sp.getPackageEnrolled());
		
		
		spf.setDateOfBirth(sp.getDateOfBirth());
		spf.setDateOfRegistration(sp.getDateOfRegistration());
		
		/** Adding Address */
		if (sp.getAddresses() != null) {
			spf.setAddress(new ArrayList<Address>());
			spf.getAddress().addAll(sp.getAddresses());
		}
		/** Adding Service */
		if(sp.getService()!= null)
			spf.setServiceName(sp.getService().getName());
		
		if (sp.getServiceAreas() != null && sp.getServiceAreas().size() > 0) {
			List<String> serviceAreas = new ArrayList<String>();
			for (ServiceArea sa : sp.getServiceAreas()) {
				serviceAreas.add(sa.getAreaName());
			}
			spf.setSelectedServiceArea(serviceAreas);
		}
		
		/** Service Provider other details */
		spf.setServiceProviderServiceDetail(sp.getServiceProviderServiceDetail());
		
		if(sp.getServiceProviderOtherDetails()!=null)
		{
			spf.setAboutUs(sp.getServiceProviderOtherDetails().getAboutUs());
		//	spf.setPhoto(photo);//FIXME
		}
		spf.setServiceProviderServiceDetail(sp.getServiceProviderServiceDetail());
		
		/** Adding Special offer */
		if( sp.getSpecialOffers() != null ) {
			spf.setSpecialOffers( new ArrayList<SpecialOffer>());
			Hibernate.initialize(sp.getSpecialOffers());
			spf.getSpecialOffers().addAll(sp.getSpecialOffers());
		}
		return spf;
	}

	protected ServiceProvider getServiceProviderOnUpdate( ServiceProviderForm serviceProviderForm) throws BusinessException {
		return getServiceProvider(serviceProviderForm);
	}

}
