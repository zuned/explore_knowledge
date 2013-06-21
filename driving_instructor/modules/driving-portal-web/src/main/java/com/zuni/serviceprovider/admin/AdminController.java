package com.zuni.serviceprovider.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.zuni.serviceprovider.domain.ServiceProviderForm;
import com.zuni.serviceprovider.domain.ServiceProviderListForm;
import com.zuni.serviceprovider.exception.BusinessException;
import com.zuni.serviceprovider.utils.DataTableUtils;
import com.zuni.serviceprovider.web.fascade.ServiceProviderFascadeService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private Logger logger = LoggerFactory.getLogger(getClass().getClass());
	
	
	@Autowired
	private ServiceProviderFascadeService serviceProviderFascadeService;
	
	@InitBinder
	protected void initBinderEmployer(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setLenient(true);
		final CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = {"", "/" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "redirect:/admin/dashboard";
	}
	
	@RequestMapping(value = {"/dashboard"}, method = RequestMethod.GET)
	public String showDashBoard(Model model) {
		return "admin/dashboard";
	}
	
	@RequestMapping(value = "/sp/{id}/image", method = RequestMethod.GET)
	public void getServiceProviderImage(@PathVariable Long id , Model model ,HttpServletResponse response) throws BusinessException, IOException {
		byte[] image = serviceProviderFascadeService.getServiceProviderImage(id);
		response.setContentType("image/jpeg");
		response.getOutputStream().write(image);
	}
	
	@ResponseBody
	@RequestMapping(value = "/instructor/list", method = RequestMethod.GET)
	public  ResponseEntity<List<ServiceProviderListForm>> getAllInstructors(Locale locale, HttpServletRequest request, HttpSession session , Model model) {
		String pageNumber = WebUtils.findParameterValue(request, "pageNumber");
		String pageSize = WebUtils.findParameterValue(request, "pageSize");
		String sortColumnNumber = WebUtils.findParameterValue(request, "sortColumnNumber");
		String order = WebUtils.findParameterValue(request, "order");

		List<ServiceProviderListForm> list = null;
		try {
			list = this.serviceProviderFascadeService.findAllActiveInstuctor(pageNumber, pageSize, sortColumnNumber, order);
		} catch (BusinessException e) {
			model.addAttribute("error", e.getLocalizedMessage());
			return new ResponseEntity<List<ServiceProviderListForm>>(list ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ServiceProviderListForm>>(list ,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/sp/list", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getEnrollments(HttpServletRequest request) {
		Map<String, Object> serviceProvider = new HashMap<String, Object>();
		try {
			String sEcho = WebUtils.findParameterValue(request, "sEcho");

			Map<String, Object> queryParams = DataTableUtils.convertDataTableParamsToQueryParams(request);
				Map<String, Object> resultMap =  this.serviceProviderFascadeService.findAllActiveInstuctor(queryParams);

				@SuppressWarnings("unchecked")
				List<ServiceProviderListForm> serviceProviderList = (List<ServiceProviderListForm>)resultMap.get("result");
				serviceProvider.put("iTotalRecords", resultMap.get("totalRecords"));
				serviceProvider.put("aaData", serviceProviderList);
				serviceProvider.put("sEcho", sEcho);
				serviceProvider.put("iTotalDisplayRecords", resultMap.get("totalRecords"));
		} catch (BusinessException be) {
			logger.warn("Error while getting all data " , be.getLocalizedMessage());
		}
		return serviceProvider;
	}
	
	@RequestMapping(value = "/sp", method = RequestMethod.GET)
	public String addServiceProvider(Model model) {
		model.addAttribute("serviceProviderForm", new ServiceProviderForm() );
		model.addAttribute("serviceAreas", serviceProviderFascadeService.getAllServiceArea());
		return "admin/addEditServiceProvider";
	}
	
	@RequestMapping(value = "/sp", method = RequestMethod.POST)
	public String createServiceProvider(@Valid ServiceProviderForm serviceProviderForm, BindingResult result,HttpServletRequest request, Model model, RedirectAttributes redirectAttrs) throws BusinessException {
		if(result.hasErrors()) {
			model.addAttribute("serviceProviderForm",serviceProviderForm );
			model.addAttribute("serviceAreas", serviceProviderFascadeService.getAllServiceArea());
			return "admin/addEditServiceProvider";
		}
		updateServiceAreas( serviceProviderForm , request.getParameterMap().get("service_area"));//TODO need to removed it with form:select / choose
		serviceProviderFascadeService.createServiceProviderProvider(serviceProviderForm);
		redirectAttrs.addFlashAttribute("message", "Provider SuccessFully Added");
		return "redirect:/admin/dashboard";
	}
	

	/**
	 * TODO
	 * try do this with JSP ONLY
	 * @param serviceProviderForm
	 * @param object
	 */
	private void updateServiceAreas(ServiceProviderForm serviceProviderForm, Object object) {
		if(object instanceof String) {
			serviceProviderForm.setSelectedServiceArea(new ArrayList<String>());
			serviceProviderForm.getSelectedServiceArea().add((String)object);
		} if ( object instanceof String[]) {
			
			serviceProviderForm.setSelectedServiceArea(new ArrayList<String>());
			String[] areas = (String[]) object;
			for(String area : areas) {
				serviceProviderForm.getSelectedServiceArea().add((String)area);
			}
		}
		
	}

	@RequestMapping(value = "/sp/{id}", method = RequestMethod.GET)
	public String getServiceProvider(@PathVariable Long id , HttpServletRequest request, Model model) throws BusinessException {
		ServiceProviderForm serviceProviderForm = serviceProviderFascadeService.getServiceProviderById(id);
		model.addAttribute("serviceProviderForm", serviceProviderForm);
		model.addAttribute("serviceAreas", serviceProviderFascadeService.getAllServiceArea());
		return "admin/addEditServiceProvider";
	}
	
	@RequestMapping(value = "/sp/{id}", method = RequestMethod.POST)
	public String updateServiceProvider(@PathVariable Long id , @Valid ServiceProviderForm serviceProviderForm, BindingResult result, Model model)  throws BusinessException {
		if(result.hasErrors()) {
			model.addAttribute("serviceProviderForm", serviceProviderForm);
			model.addAttribute("serviceAreas", serviceProviderFascadeService.getAllServiceArea());
			return "admin/addEditServiceProvider";
		}
		serviceProviderFascadeService.updateServiceProvider(id ,serviceProviderForm);
		return "redirect:/admin/dashboard";
	}
	
	@RequestMapping(value = "/sp/{id}/remove", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> removeServiceProvider(@PathVariable Long id , HttpServletRequest request, Model model)  {
		Map<String, String> map = new HashMap<String ,String >();
		Boolean result =  null;
		try{
			result =  serviceProviderFascadeService.deleteServiceProviderById(id);
		} catch(BusinessException be) {
			result = false;
			map.put("result", result.toString());
			map.put("errorMsg", be.getLocalizedMessage());
			return new ResponseEntity<Map<String, String>>(map ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		map.put("result", result.toString());
		return new ResponseEntity<Map<String, String>>(map ,HttpStatus.OK);
	}
}
