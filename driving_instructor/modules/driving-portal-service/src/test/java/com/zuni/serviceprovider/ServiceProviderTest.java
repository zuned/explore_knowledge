package com.zuni.serviceprovider;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zuni.serviceprovider.domain.ServiceProvider;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.service.ServiceProviderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/resouce.xml" )
public class ServiceProviderTest extends AbstractTestServiceProvider {

	private Long serviceProviderId = 8L;
	@Autowired
	private ServiceProviderService serviceProviderService;
	
	
	@Test
	public void createServiceInstructorTest() throws BusinessException, ParseException {
		this.serviceProviderService.createServiceProvider(super.getServiceProvider());
	}
	
	@Test
	public void updateServicePorviderTest() throws BusinessException {
		ServiceProvider sp = this.serviceProviderService.getActiveServiceProviderById(serviceProviderId);
		this.serviceProviderService.updateServiceProvider(this.serviceProviderId, sp);
	}
}
