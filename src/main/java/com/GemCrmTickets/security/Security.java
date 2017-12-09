package com.GemCrmTickets.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration

@EnableWebSecurity

public class Security extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private DataSource dataSource;
	
	
	
	
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
								 .usersByUsernameQuery("select a.email as username, a.password, a.visibility from agents a where a.email=?")
								 .authoritiesByUsernameQuery("select a1.email as username, a1.role from agents a1, agents a2 where a1.id = a2.id and a1.email=?")
								 .rolePrefix("ROLE_");
		
	}
	
	
	
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin().loginPage("/login").defaultSuccessUrl("/");
		
		http.authorizeRequests().antMatchers("/admin/**").hasRole("adm");
		
		http.authorizeRequests().antMatchers("/agent/**").hasAnyRole("dev", "sup", "adm");
		
		http.exceptionHandling().accessDeniedPage("/403");
		
	}
	
	
	
	
	
	
	
}
