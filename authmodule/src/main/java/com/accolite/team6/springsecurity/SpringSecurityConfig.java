package com.accolite.team6.springsecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//In-Memory Authentication
		auth.inMemoryAuthentication() 
		.withUser("User") 
		.password("password")
		.roles("USER") 
		.and() 
		.withUser("Admin") 
		.password("password")
		.roles("ADMIN");
		
		
		}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//Change the following Encoder with your Own Password Encoder
		return NoOpPasswordEncoder.getInstance();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		//Configuration for In-Memory Authentication
		http.authorizeRequests() 
		.antMatchers("/admin")
		.hasRole("ADMIN")
		.antMatchers("/user")
		.hasAnyRole("ADMIN","USER")
		.antMatchers("/")
		.permitAll()
		.antMatchers("/login")
		.permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.and()
		.oauth2Login();
		
		//Configuration for Google OAuth 2.0
		//http
		//.antMatcher("/**").authorizeRequests()
		//.antMatchers("/").permitAll()
		//.anyRequest().authenticated()
		//.and()
		//.oauth2Login();
	}
	
}
