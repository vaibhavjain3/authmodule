package com.accolite.team6.springsecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomePage {
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String home() {
		return "index";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/help")
	public String restricted() {
		return "help";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/user")
	public String user() {
		return "user";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public String admin() {
		return "admin";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String login() {
		return "login";
	}
	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	public String logout() {
		return "logout";
	}
	
}
