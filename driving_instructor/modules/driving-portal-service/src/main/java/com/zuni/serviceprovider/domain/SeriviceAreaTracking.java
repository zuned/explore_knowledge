package com.zuni.serviceprovider.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Zuned Ahmed
 * 
 */
@Entity
@Table(name = "service_area_tracking")
public class SeriviceAreaTracking extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "service_name")
	private String serviceName;
	
	@Column(name = "no_result_found")
	private Boolean noResultFound;
	
	@ManyToOne
	@JoinColumn(name = "service_area_id")
	private ServiceArea ServiceArea;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ServiceArea getServiceArea() {
		return ServiceArea;
	}

	public void setServiceArea(ServiceArea serviceArea) {
		ServiceArea = serviceArea;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Boolean getNoResultFound() {
		return noResultFound;
	}

	public void setNoResultFound(Boolean noResultFound) {
		this.noResultFound = noResultFound;
	}

}
