package com.zuni.serviceprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zuni.serviceprovider.constant.ConstantUtil.STATUS;
import com.zuni.serviceprovider.domain.Packages;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface PackageRepository extends JpaRepository<Packages, Long>  {

	Packages findByNameIgnoreCaseAndStatus(String name, STATUS active);

}
