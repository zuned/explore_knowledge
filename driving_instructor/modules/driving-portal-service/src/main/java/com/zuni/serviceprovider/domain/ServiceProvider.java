package com.zuni.serviceprovider.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zuni.serviceprovider.constant.ConstantUtil.STATUS;

/**
 * 
 * @author Zuned Ahmed
 * 
 */
@Entity
@Table(name = "service_provider")
public class ServiceProvider extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name = "date_of_registration")
	@Temporal(TemporalType.DATE)
	private Date dateOfRegistration;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private STATUS status;

	@Column(name = "gender")
	private String gender;

	@Column(name = "licence_number")
	private String licenceNumber;

	@Column(name = "contact_preference")
	private String contactPrefrence;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "member_type")
	private String memberType;

	@Column(name = "company_name")
	private String companyName;//FIXEME one to one mapping with company name

	@OneToOne(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private ServiceProviderOtherDetails serviceProviderOtherDetails;

	@OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Address> addresses;

	@OneToOne(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private PackageEnrolled packageEnrolled;

	@OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<SpecialOffer> specialOffers;
	
	@OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Testimonial> testimonials;
	
	@OneToOne(mappedBy="serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private ServiceProviderServiceDetail serviceProviderServiceDetail;
	
	@ManyToMany(mappedBy="serviceProviders")
	private Set<ServiceArea> serviceAreas; 
	
	@ManyToOne
	@JoinColumn(name="service_id")
	private Services service;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getContactPrefrence() {
		return contactPrefrence;
	}

	public void setContactPrefrence(String contactPrefrence) {
		this.contactPrefrence = contactPrefrence;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public ServiceProviderOtherDetails getServiceProviderOtherDetails() {
		return serviceProviderOtherDetails;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public PackageEnrolled getPackageEnrolled() {
		return packageEnrolled;
	}

	public void setPackageEnrolled(PackageEnrolled packageEnrolled) {
		this.packageEnrolled = packageEnrolled;
	}

	public Set<SpecialOffer> getSpecialOffers() {
		return specialOffers;
	}

	public void setSpecialOffers(Set<SpecialOffer> specialOffers) {
		if(this.specialOffers==null){
			this.specialOffers = specialOffers;
		} else {
		this.specialOffers.clear();
		if(specialOffers!=null)
			this.specialOffers.addAll(specialOffers);
		}
	}

	public Set<Testimonial> getTestimonials() {
		return testimonials;
	}

	public void setTestimonials(Set<Testimonial> testimonials) {
		this.testimonials = testimonials;
	}

	public void setServiceProviderServiceDetail(ServiceProviderServiceDetail serviceProviderServiceDetail) {
		this.serviceProviderServiceDetail =serviceProviderServiceDetail;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Set<ServiceArea> getServiceAreas() {
		return serviceAreas;
	}

	public void setServiceAreas(Set<ServiceArea> serviceAreas) {
		this.serviceAreas = serviceAreas;
	}
	
	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public ServiceProviderServiceDetail getServiceProviderServiceDetail() {
		return serviceProviderServiceDetail;
	}

	public void setServiceProviderOtherDetails(
			ServiceProviderOtherDetails serviceProviderOtherDetails) {
		this.serviceProviderOtherDetails = serviceProviderOtherDetails;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}
}
