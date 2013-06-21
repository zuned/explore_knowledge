package com.zuni.serviceprovider.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Zuned Ahmed
 * 
 */
@Entity
@Table(name="service_provider_service_detail")
public class ServiceProviderServiceDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "service_start_date")
	@Temporal(TemporalType.DATE)
	private Date serviceStartDate;

	@Column(name = "sprciality")
	private String speciality;
	
	@Column(name = "timing")
	private String timing;

	@Column(name = "qualification")
	private String qualification;
	
	@OneToOne
	@JoinColumn(name = "service_provider_id")
	private ServiceProvider serviceProvider;
	
	@OneToMany(mappedBy="serviceProviderServiceDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ServiceExtraInfo> serviceExtraInfo;


	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Set<ServiceExtraInfo> getServiceExtraInfo() {
		return serviceExtraInfo;
	}

	public void setServiceExtraInfo(Set<ServiceExtraInfo> serviceExtraInfo) {
		this.serviceExtraInfo = serviceExtraInfo;
	}
}
