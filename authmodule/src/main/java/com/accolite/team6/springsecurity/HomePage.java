package com.accolite.team6.springsecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomePage {
	//Will be replacing strings with HTML pages
	@GetMapping("/")
	public String home() {
		return ("<h1>Welcome to AuthModule Application<h1>");
	}
	@GetMapping("/restricted")
	public String restricted() {
		return ("<h1>Welcome Google User<h1>");
	}
	@GetMapping("/user")
	public String user() {
		return ("<h1>Welcome User<h1>");
	}
	@GetMapping("/admin")
	public String admin() {
		return ("<h1>Welcome Admin<h1>");
	}
}
