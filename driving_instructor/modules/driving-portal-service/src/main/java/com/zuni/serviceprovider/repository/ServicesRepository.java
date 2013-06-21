package com.zuni.serviceprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zuni.serviceprovider.domain.Services;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface ServicesRepository extends JpaRepository<Services, Long>  {

	Services findByNameIgnoreCase(String name);

}
