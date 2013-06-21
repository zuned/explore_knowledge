package com.zuni.serviceprovider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zuni.serviceprovider.domain.PackageEnrolled;
import com.zuni.serviceprovider.domain.Packages;
import com.zuni.serviceprovider.repository.PackageEnrolledRepository;
import com.zuni.serviceprovider.repository.PackageRepository;
import com.zuni.serviceprovider.service.PackageService;
/**
 * 
 * @author Zuned Ahmed
 *
 */
@Transactional(readOnly=true)
@Service
public class PackageServiceImpl implements PackageService {

	@Autowired
	private PackageRepository packageRepository;
	
	@Autowired
	private PackageEnrolledRepository packageEnrolledRepository;
	
	@Override
	public PackageEnrolled assginAndActivatePackageToServiceProvider( Long serviceProviderId, String PackageName, boolean activate) {
		PackageEnrolled pckEnrolled = new PackageEnrolled();//validate package , validate driving instructor id  
//			pckEnrolled.setDrivingInstructor(drivingInstructor);
//			pckEnrolled.setPackages(packages);
//			pckEnrolled.setStartDate(startDate);
//			pckEnrolled.setStatus(status);
		return packageEnrolledRepository.save(pckEnrolled);
	}

	@Override
	public PackageEnrolled unAssginPackageFromDrivingInstructor( Long serviceProviderId, String PackageName) {
		PackageEnrolled pckEnrolled = new PackageEnrolled();
//		pckEnrolled.setDrivingInstructor(drivingInstructor);
//		pckEnrolled.setPackages(packages);
//		pckEnrolled.setStartDate(startDate);
//		pckEnrolled.setStatus(status);
		return packageEnrolledRepository.save(pckEnrolled);
	}

	@Override
	public List<Packages> getAllPackagesAvailable() {
		return this.packageRepository.findAll();//FIXME only active packages
	}

}
