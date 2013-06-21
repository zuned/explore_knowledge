package com.zuni.serviceprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zuni.serviceprovider.domain.PackageEnrolled;
/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface PackageEnrolledRepository extends JpaRepository<PackageEnrolled, Long> {

}
