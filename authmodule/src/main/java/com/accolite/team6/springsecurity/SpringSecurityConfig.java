package com.accolite.team6.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//This class defines the configuration of Spring Security for the application
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService UserService;	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		//In-Memory Authentication
		 auth.inMemoryAuthentication() 
		//Customize the following User-names, Passwords and Roles as per your needs
		 	 .withUser("User")
		 	 .password("pass")
		 	 .roles("USER") 
		 	 .and() 
		 	 .withUser("Admin") 
		 	 .password("pass")
		 	 .roles("ADMIN");				
		
		 //Database Authentication using Hibernate
		auth.userDetailsService(UserService);
	
	}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//Change the following Encoder with your own Password Encoder
		return NoOpPasswordEncoder.getInstance();
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
	
		//Configuration of the requests handled by the application.
		//It has been customized for 2-Role Authorization: Administrator (ADMIN) and a normal User (USER)
		http.authorizeRequests()
		//'admin' pages can only be accessed by ADMIN
		.antMatchers("/admin**") 
		.hasRole("ADMIN")
		//'user' pages can only be accessed by both USER and ADMIN
		.antMatchers("/user**")
		.hasAnyRole("ADMIN","USER")
		//Help and documentation, style-sheets and login-errors are accessible to all even without authentication
		.antMatchers("/help","/*.css","/login?error")
		.permitAll()
		//Any other resource is only accessible after authentication
		.antMatchers("/**")
		.hasAnyRole("ADMIN","USER")
		.and()
		
		//Enabling In-Memory and Database Authentication
		.formLogin()
		//Customized login page accessible to all
		.loginPage("/login")
		.permitAll()
		//Always re-directing to the '/',i.e., index.html page after authentication
		.defaultSuccessUrl("/", true)
		.and()
		
		//Enabling OAuth 2.0 Authentication
		.oauth2Login()
		//Customized login page
		.loginPage("/login")
		.permitAll()
		//Always re-directing to the '/',i.e., index.html page after authentication
		.defaultSuccessUrl("/", true)
		.and()
		
		//Customized logout page
		.logout()
		.deleteCookies("remove")
		.invalidateHttpSession(true)
		.logoutUrl("/logout")
		//Re-directing to custom login page after logging out
		.logoutSuccessUrl("/login?logout")
		.permitAll();
		
	}	
}
