package com.zuni.serviceprovider.service;

import java.util.List;

import com.zuni.serviceprovider.domain.PackageEnrolled;
import com.zuni.serviceprovider.domain.Packages;
import com.zuni.serviceprovider.exception.BusinessException;
/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface PackageService {
	
	PackageEnrolled assginAndActivatePackageToServiceProvider(Long serviceProviderId , String PackageName , boolean activate) throws BusinessException;
	
	PackageEnrolled unAssginPackageFromDrivingInstructor(Long serviceProviderId , String PackageName) throws BusinessException;

	List<Packages> getAllPackagesAvailable() throws BusinessException;
}
