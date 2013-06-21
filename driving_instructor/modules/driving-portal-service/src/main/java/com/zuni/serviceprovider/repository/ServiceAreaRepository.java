package com.zuni.serviceprovider.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zuni.serviceprovider.domain.ServiceArea;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface ServiceAreaRepository  extends JpaRepository<ServiceArea, Long>{

	List<ServiceArea> findByAreaNameIn(Collection<String> serviceAears);

}
