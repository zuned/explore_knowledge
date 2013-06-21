package com.zuni.serviceprovider;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author admin
 *
 */
@Controller
public class HomeController {

	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String showHomePage(){
		return "index";
	}
}
