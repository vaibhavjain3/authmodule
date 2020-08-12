package com.accolite.team6.springsecurity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//This class defines the User object that will be retrieved from the database for passing to Spring Security for authentication
@Entity
@Table(name="user")
@Transactional
@Service
public class CustomUserDetails implements UserDetails {
	
	private static SessionFactory sessionFactory;
	//Binding data members to database attributes
	@Id
	@Column(name="id")
	private int id;
	@Column(name="user_name")
	private String user_name;
	@Column(name="password")
	private String password;
	@Column(name="roles")
	private String role;
	
	//If additional attributes are present in your database, uncomment and add their 'name' in '@Column' of the following member variables to link them to your database attributes
	//You can also add additional member variables for other database attributes in a similar manner
	/* 
	 * @Column(name="")
	 * private boolean is_account_non_expired;
	 * @Column(name="")
	 * private boolean is_account_non_locked;
	 * @Column(name="")
	 * private boolean is_credentials_non_expired;
	 * @Column(name="")
	 * private boolean is_enabled;
	 */
	
	//Parameterized Constructor
	public CustomUserDetails(String username) {
		
		//Fetching details of the User from the database
		sessionFactory = new Configuration().configure().addAnnotatedClass(CustomUserDetails.class).buildSessionFactory();
		Session session = sessionFactory.openSession();	
		Criteria criteria = session.createCriteria(CustomUserDetails.class);
		CustomUserDetails user = (CustomUserDetails) criteria.add(Restrictions.eq("user_name", username)).uniqueResult();
		if(user!=null) {
			this.id=user.getId();
			this.user_name=user.getUsername();		
			this.password=user.getPassword();
			this.role=user.getRole();
			//Uncomment for additional database attributes
			/* 
			 * this.is_account_non_expired=user.isAccountNonExpired();
			 * this.is_account_non_locked=user.isAccountNonLocked();
			 * this.is_credentials_non_expired=user.isCredentialsNonExpired();
			 * this.is_enabled=user.isEnabled();
			 */
		}
		else {
			id=0;
			password=null;
			role=null;
			user_name=null;
		}
		session.close();
	}
	
	//Constructor 
	public CustomUserDetails() {
	}
	
	//Fetching details of all Users from the database
	public List<CustomUserDetails> getAllUsers() {
		
		sessionFactory = new Configuration().configure().addAnnotatedClass(CustomUserDetails.class).buildSessionFactory();
		Session session = sessionFactory.openSession();	
		List<CustomUserDetails> list = session.createCriteria(CustomUserDetails.class).list();
		return list;
	}
	
	public int getId() {
		return id;
	}
	
	public String getRole() {
		return role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+role));
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return user_name;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
		//return is_account_non_expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
		//return is_account_non_locked;	
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
		//return is_credentials_non_expired;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
		//return is_enabled;
	}
	
}
