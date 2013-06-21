package com.zuni.serviceprovider.domain;

import java.util.Date;

public class ServiceProviderListForm {

	private Long id;
	
	private String name;
	
	private String memberType;
	
	private String areaCodes;
	
	private Date dateOfRegistration;
	
	private String licensce;
	
	private String contactPrefrence;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(String areaCodes) {
		this.areaCodes = areaCodes;
	}

	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public String getLicensce() {
		return licensce;
	}

	public void setLicensce(String licensce) {
		this.licensce = licensce;
	}

	public String getContactPrefrence() {
		return contactPrefrence;
	}

	public void setContactPrefrence(String contactPrefrence) {
		this.contactPrefrence = contactPrefrence;
	}
}
