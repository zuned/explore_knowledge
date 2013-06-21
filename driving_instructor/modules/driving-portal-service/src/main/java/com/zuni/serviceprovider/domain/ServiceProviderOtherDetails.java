package com.zuni.serviceprovider.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Zuned Ahmed
 * 
 */
@Entity
@Table(name = "service_provider_other_details")
public class ServiceProviderOtherDetails extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Lob @Basic(fetch=FetchType.LAZY)
	@Column(name = "image")
	private byte[] image;

	@Column(name = "about_us")
	private String aboutUs;

	@OneToOne(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "service_provider_id")
	private ServiceProvider serviceProvider;

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
