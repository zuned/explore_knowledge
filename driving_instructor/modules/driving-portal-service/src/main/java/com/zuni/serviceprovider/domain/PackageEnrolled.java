package com.zuni.serviceprovider.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "package_enrolled")
public class PackageEnrolled extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private STATUS status;

	@OneToOne
	@JoinColumn(name = "service_provider_id")
	private ServiceProvider serviceProvider;

	@ManyToOne
	@JoinColumn(name = "package_id")
	private Packages packages;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Packages getPackages() {
		return packages;
	}

	public void setPackages(Packages packages) {
		this.packages = packages;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}
}
