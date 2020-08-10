package com.accolite.team6.springsecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomePage {
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String home() {
		return "home_page";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/restricted")
	public String restricted() {
		return "google_user";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/user")
	public String user() {
		return "user";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public String admin() {
		return "admin";
	}
}
