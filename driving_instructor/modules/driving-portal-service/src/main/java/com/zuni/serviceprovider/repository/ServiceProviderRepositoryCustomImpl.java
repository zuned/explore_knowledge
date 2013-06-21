package com.zuni.serviceprovider.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.zuni.serviceprovider.domain.ServiceProviderTracking;
import com.zuni.serviceprovider.report.domain.ReportRequestObject;


/**
 * 
 * @author Zuned Ahmed
 *
 */
public class ServiceProviderRepositoryCustomImpl implements ServiceProviderRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceProviderTracking> findLeastFavorableServiceProvider( ReportRequestObject rROb) {
		final Criteria searchCriteria = ((Session) entityManager.getDelegate()).createCriteria(ServiceProviderTracking.class);
		Map<String, Object> queryParameterMap = rROb.getQueryParameters();
		if (queryParameterMap != null) {
			for (Entry<String, Object> entry : queryParameterMap.entrySet()) {
				getCriterionForEntry(searchCriteria , entry.getKey(), entry.getValue());
			}
		}
		return searchCriteria.list();
	}

	private void getCriterionForEntry(final Criteria searchCriteria , final String key,final Object value) {
		if("startDate".equalsIgnoreCase(key)) {
			searchCriteria.add(getStartDateCriteria((Date)value));
		}
		if("endDate".equalsIgnoreCase(key)) {
			searchCriteria.add( getEndDateCriteria((Date)value) );
		}
		
		if("serviceName".equalsIgnoreCase(key)) {
			if(value!=null) {
				String name[] = ((String)value).split(" ");
				if( name.length == 1) {
					searchCriteria.add(getServicePoviderFirstNameCriteria(name[0]));
					searchCriteria.add(getServicePoviderLastNameCriteria(name[0]));
					searchCriteria.add(getServicePoviderMiddleNameCriteria(name[0]));
				} if(name.length == 2) {
					searchCriteria.add(getServicePoviderFirstNameCriteria(name[0]));
					searchCriteria.add(getServicePoviderLastNameCriteria(name[1]));
				} if( name.length == 3) {
					searchCriteria.add(getServicePoviderFirstNameCriteria(name[0]));
					searchCriteria.add(getServicePoviderLastNameCriteria(name[2]));
					searchCriteria.add(getServicePoviderMiddleNameCriteria(name[1]));
				}
			}
			
		}
		
		if("numberOFRecord".equalsIgnoreCase(key)) {
			searchCriteria.setFetchSize((Integer)value);
		}
	}

	private Criterion getServicePoviderFirstNameCriteria(String value) {
		return Restrictions.like("serviceProvider.firstName", value , MatchMode.ANYWHERE);
	}
	
	private Criterion getServicePoviderMiddleNameCriteria(String value) {
		return Restrictions.like("serviceProvider.middleName", value , MatchMode.ANYWHERE);
	}
	
	private Criterion getServicePoviderLastNameCriteria(String value) {
		return Restrictions.like("serviceProvider.lastName", value , MatchMode.ANYWHERE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceProviderTracking> findMostFavorableServiceProvider( ReportRequestObject rROb) {
		final Criteria searchCriteria = ((Session) entityManager.getDelegate()).createCriteria(ServiceProviderTracking.class);
		Map<String, Object> queryParameterMap = rROb.getQueryParameters();
		if (queryParameterMap != null) {
			for (Entry<String, Object> entry : queryParameterMap.entrySet()) {
				getCriterionForEntry(searchCriteria , entry.getKey(), entry.getValue());
			}
		}
		return searchCriteria.list();
	}

	
	private SimpleExpression getStartDateCriteria( Date date) {
		return Restrictions.ge("createdDate", date);
	}

	private SimpleExpression getEndDateCriteria( Date date) {
		return Restrictions.le("createdDate", date);
	}
}
