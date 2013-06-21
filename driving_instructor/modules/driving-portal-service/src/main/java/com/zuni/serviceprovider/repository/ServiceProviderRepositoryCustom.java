package com.zuni.serviceprovider.repository;

import java.util.List;

import com.zuni.serviceprovider.domain.ServiceProviderTracking;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;

/**
 * 
 * @author Zuned Ahmed
 *
 */
public interface ServiceProviderRepositoryCustom {

	List<ServiceProviderTracking> findLeastFavorableServiceProvider( ReportRequestObject rROb);

	List<ServiceProviderTracking> findMostFavorableServiceProvider( ReportRequestObject rROb);

}
