package com.zuni.serviceprovider.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zuni.serviceprovider.constant.ConstantUtil.STATUS;
import com.zuni.serviceprovider.domain.ServiceProvider;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

	Page<ServiceProvider> findByStatus(STATUS active, Pageable pagenable);

	ServiceProvider findByIdAndStatus(Long serviceProviderId, STATUS active);

	List<ServiceProvider> findByStatus(STATUS active, Sort sort);

	List<ServiceProvider> findByStatusAndFirstNameOrLastName(STATUS active, String firstName, String lastName);

	Page<ServiceProvider> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingAndStatus( String firstName, String lastName, STATUS active, Pageable pagenable);

	Page<ServiceProvider> findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndStatus(String firstName, String lastName, STATUS active, Pageable pagenable);

	Page<ServiceProvider> findByFirstNameIgnoreCaseAndMiddleNameIgnoreCaseAndLastNameIgnoreCaseAndStatus( String firstName, String lastName, String middleName, STATUS active,
			Pageable pagenable);

	List<ServiceProvider> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingAndStatus( String firstName, String lastName, STATUS active, Sort sort);

	List<ServiceProvider> findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndStatus(String firstName, String lastName, STATUS active, Sort sort);

	List<ServiceProvider> findByFirstNameIgnoreCaseAndMiddleNameIgnoreCaseAndLastNameIgnoreCaseAndStatus( String firstName, String lastName, String middleName,
			STATUS active, Sort sort);

}
