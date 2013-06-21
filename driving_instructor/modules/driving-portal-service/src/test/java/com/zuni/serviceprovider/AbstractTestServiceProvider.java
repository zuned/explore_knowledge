package com.zuni.serviceprovider;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.zuni.serviceprovider.constant.ConstantUtil;
import com.zuni.serviceprovider.constant.ConstantUtil.STATUS;
import com.zuni.serviceprovider.domain.Address;
import com.zuni.serviceprovider.domain.PackageEnrolled;
import com.zuni.serviceprovider.domain.Packages;
import com.zuni.serviceprovider.domain.ServiceArea;
import com.zuni.serviceprovider.domain.ServiceExtraInfo;
import com.zuni.serviceprovider.domain.ServiceProvider;
import com.zuni.serviceprovider.domain.ServiceProviderOtherDetails;
import com.zuni.serviceprovider.domain.ServiceProviderServiceDetail;
import com.zuni.serviceprovider.domain.SpecialOffer;
import com.zuni.serviceprovider.domain.Testimonial;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public abstract class AbstractTestServiceProvider {
	
	@Autowired
	private ConstantUtil constantUtil;

	protected ServiceProvider getServiceProvider() throws ParseException {
		ServiceProvider serviceProvider = new ServiceProvider();
			serviceProvider.setCompanyName("Driving Instructor Comp.");
			serviceProvider.setContactPrefrence("EMAIL");
			serviceProvider.setDateOfBirth(constantUtil.getDateFormatter().parse("11/23/1986"));
			serviceProvider.setEmailId("zuned485@yahoo.com");
			serviceProvider.setFirstName("zuned");
			serviceProvider.setLastName("ahmed");
			serviceProvider.setGender(constantUtil.getGENDER()[0]);
			serviceProvider.setLicenceNumber("1231231231");
			serviceProvider.setMemberType("FREE");
			serviceProvider.setStatus(STATUS.ACTIVE);
			
			serviceProvider.setAddresses(getAddresses());
			serviceProvider.setServiceProviderServiceDetail(getServiceProviderServiceDetail());
			serviceProvider.setServiceProviderOtherDetails(getServiceProviderOtherDetails());
			//serviceProvider.setPackageEnrolled(getPackageEnrolled());
			serviceProvider.setSpecialOffers(getSpecialOffers());
			serviceProvider.setTestimonials(getTestimonials());
		//	serviceProvider.setServiceAreas(getServiceArea());
			
		return serviceProvider;
	}

	private Set<ServiceArea> getServiceArea() {
		Set<ServiceArea> saList = new HashSet<ServiceArea>();
		ServiceArea sa = new ServiceArea();
		sa.setPostCode("AB23");
		saList.add(sa);
		return saList;
	}

	protected Set<Address> getAddresses() {
		Set<Address> addresses = new HashSet<Address>();
			Address address = new Address();
				address.setAddressType(constantUtil.getADDRESS_TYPE()[0]);
				address.setBuildingName("Building Name");
				address.setStreetAddress("Street Address");
				address.setCity("Birmingham");
				address.setCounty("West Midlands");
				address.setPostCode("B38");
				address.setPrimaryPhoneNumber("8800887321");
				address.setSecondaryPhoneNumber("5345767567");
		addresses.add(address);
		return addresses;
	}

	protected ServiceProviderServiceDetail getServiceProviderServiceDetail() {
		ServiceProviderServiceDetail serviceProviderServiceDetail = new ServiceProviderServiceDetail();
			serviceProviderServiceDetail.setServiceStartDate(new Date());
			serviceProviderServiceDetail.setQualification("MCA");
			serviceProviderServiceDetail.setSpeciality("special");
			serviceProviderServiceDetail.setTiming("anytime");
			serviceProviderServiceDetail.setServiceExtraInfo(getServiceExtraInfo());
		return serviceProviderServiceDetail;
	}
	
	private Set<ServiceExtraInfo> getServiceExtraInfo() {
		Set<ServiceExtraInfo> setSEI = new HashSet<ServiceExtraInfo>();
		ServiceExtraInfo isNervousDriver = new ServiceExtraInfo();
			isNervousDriver.setProperty("isNervousDriver");
			isNervousDriver.setValue("false");
			setSEI.add(isNervousDriver);
		ServiceExtraInfo vehicleType = new ServiceExtraInfo();
			vehicleType.setProperty("vehicleType");
			vehicleType.setValue("AUTOMATIC");
			setSEI.add(vehicleType);
		return setSEI;
	}

	protected ServiceProviderOtherDetails getServiceProviderOtherDetails() {
		ServiceProviderOtherDetails serviceProviderOtherDetails = new ServiceProviderOtherDetails();
			serviceProviderOtherDetails.setAboutUs("About Us");
			serviceProviderOtherDetails.setImage("Image".getBytes());
		return serviceProviderOtherDetails;
	}
	
	protected PackageEnrolled getPackageEnrolled() {
		PackageEnrolled packageEnrolled = new PackageEnrolled();
			packageEnrolled.setPackages(getPackages());
			packageEnrolled.setStartDate(new Date());
			packageEnrolled.setStatus(STATUS.ACTIVE);
		return packageEnrolled;
	}

	protected Packages getPackages() {
		Packages packages = new Packages();
			packages.setCreatedDate(new Date());
			packages.setDuration(constantUtil.getPACKAGE_DURATION()[0]);
			packages.setName("FREE");
			packages.setId(1L);
			packages.setStatus(STATUS.ACTIVE);
		return packages;
	}

	protected Set<SpecialOffer> getSpecialOffers() {
		SpecialOffer sof = new SpecialOffer();
			sof.setDescription("Special Offer Description");
			sof.setName("SpecialOffer");
			sof.setStatus(STATUS.ACTIVE);
			sof.setStartDate(new Date());
			sof.setEndDate(new Date());
		Set<SpecialOffer> sofs = new HashSet<SpecialOffer>();
			sofs.add(sof);
		return sofs;
	}
	
	protected Set<Testimonial> getTestimonials() {
		Set<Testimonial> setTest = new HashSet<Testimonial>();
			Testimonial test = new Testimonial();
				setTest.add(test);
			test.setCreatedBy("junit");
			test.setCreatedDate(new Date());
			test.setDesc("Good Job");
			test.setRelationShip(constantUtil.getRELATIONSHIP()[0]);
		return setTest;
	}

}
