package com.zuni.serviceprovider.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * this table will contain service related extra info 
 * for eg: in case of DI , it will keep vehicle_type ,isNervousDriver
 * @author admin
 *
 */
@Entity
@Table(name="service_extra_info")
public class ServiceExtraInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="property")
	private String property;
	
	@Column(name="value")
	private String value;
	
	@ManyToOne
	@JoinColumn(name="service_provider_service_detail_id")
	private ServiceProviderServiceDetail serviceProviderServiceDetail;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ServiceProviderServiceDetail getServiceProviderServiceDetail() {
		return serviceProviderServiceDetail;
	}

	public void setServiceProviderServiceDetail(
			ServiceProviderServiceDetail serviceProviderServiceDetail) {
		this.serviceProviderServiceDetail = serviceProviderServiceDetail;
	}
}
