package com.zuni.serviceprovider.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.collections.list.AbstractLinkedList;
/**
 * 
 * @author Zuned Ahmed
 *
 */
@Entity
@Table(name="service_area")
public class ServiceArea   extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="area_name")
	private String areaName;
	
	@Column(name="post_code")
	private String postCode;
	
	@OneToMany(mappedBy = "ServiceArea", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<SeriviceAreaTracking>  SeriviceAreaTracking;
	
	@ManyToMany
	@JoinTable(name="service_area_covered", joinColumns = {@JoinColumn(name = "service_area_id") }, inverseJoinColumns = { @JoinColumn(name = "service_provider_id") })
	private Set<ServiceProvider> serviceProviders; 
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Set<SeriviceAreaTracking> getSeriviceAreaTracking() {
		return SeriviceAreaTracking;
	}
	public void setSeriviceAreaTracking(
			Set<SeriviceAreaTracking> seriviceAreaTracking) {
		SeriviceAreaTracking = seriviceAreaTracking;
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
	public Set<ServiceProvider> getServiceProviders() {
		return serviceProviders;
	}
	public void setServiceProviders(Set<ServiceProvider> serviceProviders) {
		this.serviceProviders = serviceProviders;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((areaName == null) ? 0 : areaName.hashCode());
		result = prime * result
				+ ((postCode == null) ? 0 : postCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceArea other = (ServiceArea) obj;
		if (areaName == null) {
			if (other.areaName != null)
				return false;
		} else if (!areaName.equals(other.areaName))
			return false;
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		return true;
	}
	
	
}
