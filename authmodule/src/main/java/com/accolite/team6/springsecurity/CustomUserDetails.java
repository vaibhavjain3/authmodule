package com.accolite.team6.springsecurity;

import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity
@Table(name="user")
@Transactional
public class CustomUserDetails implements UserDetails {
	
	private static SessionFactory sessionFactory;
	@Id
	@Column(name="id")
	private int id;
	@Column(name="user_name")
	private String user_name;
	@Column(name="password")
	private String password;
	@Column(name="roles")
	private String role;
	
	//Returning these values
	public int get_id(){
		return id;
	}
	public String get_user_name(){
		return user_name;
	}
	public String get_password() {
		return password;
	}
	public String get_role() {
		return role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + user_name + ","+ "role=" + role + "]";
	}
	//Constructor
	public CustomUserDetails(String username){
	
		sessionFactory = new Configuration().configure().addAnnotatedClass(CustomUserDetails.class).buildSessionFactory();
		Session session = sessionFactory.openSession();	
		CustomUserDetails user = session.get(CustomUserDetails.class, 4);
		if(user!=null)
		{
			this.id=user.get_id();
			this.user_name=user.get_user_name();		
			this.password=user.get_password();
			this.role=user.get_role();
		}
		else
		{
			id=0;
			password=null;
			role=null;
			user_name=null;

		}
		session.close();
	}
	public CustomUserDetails(){
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+role));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user_name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
