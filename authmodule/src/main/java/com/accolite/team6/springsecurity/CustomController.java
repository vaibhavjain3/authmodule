package com.accolite.team6.springsecurity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//This class defines how the requests will be handled by the application
@Controller
public class CustomController {
	
	@Autowired
	private CustomUserDetails userDetails;
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String home() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/help")
	public ModelAndView method() {
	    return new ModelAndView("redirect:" + "https://ayushmalikofficial.github.io/documentation/");
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/user")
	public String user() {
		return "user";
	}
		
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	public String logout() {
		return "logout";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/error")
	public String error() {
		return "error";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public String admin(Model model) {
		List<CustomUserDetails> user = userDetails.getAllUsers();
		List<String> username = new ArrayList<String>();
		List<String> adminname = new ArrayList<String>();
		for(int i=0;i<user.size();i++) {
		 if(user.get(i).getRole().equals("ADMIN")) {
			 adminname.add(user.get(i).getUsername());
		 }
		 else {
			 username.add(user.get(i).getUsername());
		 }
		}
		model.addAttribute("user", username);     
		model.addAttribute("admin", adminname);
		return "admin";
	}

}
