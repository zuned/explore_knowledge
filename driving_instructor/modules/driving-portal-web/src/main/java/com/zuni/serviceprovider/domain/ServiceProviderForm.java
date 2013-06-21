package com.zuni.serviceprovider.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class ServiceProviderForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message="First Name required.")
	@NotNull(message="First Name required.")
//	@Pattern(regexp="" , message="")
	private String firstName;

	private String middleName;

	@NotEmpty(message="Last Name required.")
	@NotNull(message="Last Name required.")
//	@Pattern(regexp="" , message="")
	private String lastName;

//	@Past(message="Invalid Date Of Birth.")
	private Date dateOfBirth;
	
	private Date dateOfRegistration;

	@NotEmpty
	@NotNull
	private String gender;

	private String licenceNumber;

	private String contactPrefrence;

	@NotNull(message="Email Id required.")
	@Email(message="Invalid Email Id.")
	private String emailId;

	private String memberType;
	
	private MultipartFile photo;
	
	private List<Address> address;
	
	private String companyName;
	
	private String serviceName;
	
	private String aboutUs;
	
	private List<String> selectedServiceArea;
	
	private List<SpecialOffer> specialOffers;
	
	private ServiceProviderServiceDetail serviceProviderServiceDetail;
	
	private PackageEnrolled packageEnrolled;

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

	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<String> getSelectedServiceArea() {
		return selectedServiceArea;
	}

	public void setSelectedServiceArea(List<String> serviceAears) {
		this.selectedServiceArea = serviceAears;
	}

	public List<SpecialOffer> getSpecialOffers() {
		return specialOffers;
	}

	public void setSpecialOffers(List<SpecialOffer> specialOffers) {
		this.specialOffers = specialOffers;
	}

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}

	public ServiceProviderServiceDetail getServiceProviderServiceDetail() {
		return serviceProviderServiceDetail;
	}

	public void setServiceProviderServiceDetail(
			ServiceProviderServiceDetail serviceProviderServiceDetail) {
		this.serviceProviderServiceDetail = serviceProviderServiceDetail;
	}

	public PackageEnrolled getPackageEnrolled() {
		return packageEnrolled;
	}

	public void setPackageEnrolled(PackageEnrolled packageEnrolled) {
		this.packageEnrolled = packageEnrolled;
	}
}
