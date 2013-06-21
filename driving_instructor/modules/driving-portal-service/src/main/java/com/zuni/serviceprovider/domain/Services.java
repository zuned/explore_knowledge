package com.zuni.serviceprovider.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="services")
public class Services extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name="service_name")
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
