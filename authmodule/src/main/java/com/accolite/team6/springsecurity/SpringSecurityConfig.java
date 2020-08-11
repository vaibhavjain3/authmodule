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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService UserService;	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//In-Memory Authentication
		 auth.inMemoryAuthentication() .withUser("User") .password("pass")
		 .roles("USER") .and() .withUser("Admin") .password("pass")
		 .roles("ADMIN");				
		//Database Authentication using Hibernate
		auth.userDetailsService(UserService);
	}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//Change the following Encoder with your Own Password Encoder
		return NoOpPasswordEncoder.getInstance();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception{
	
		//Configuration for Google OAuth 2.0
				/*
				 * http .antMatcher("/**").authorizeRequests() .antMatchers("/").permitAll()
				 * .anyRequest().authenticated() .and() .oauth2Login();
				 */	
		http.authorizeRequests()
			.antMatchers("/admin**") 
			.hasRole("ADMIN")
			.antMatchers("/user**") 
			.hasAnyRole("ADMIN","USER")
			.antMatchers("/help")
			.permitAll()
			.antMatchers("/**")
			.hasAnyRole("ADMIN","USER")
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.permitAll()
			.and()
			.logout()
			.deleteCookies("remove")
			.invalidateHttpSession(false)
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login?logout")
			.permitAll();
	}	
}
