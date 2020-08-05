package com.santosh.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/* In memory authentication
		 * auth.inMemoryAuthentication().withUser("santosh").password("iamsantosh").
		 * roles("ADMIN").and() .withUser("kumar").password("kumar").roles("USER");
		 */
		
		//JDBC authentication for inbuild H2 database
		/*
		 * auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema() //
		 * create user table and authority table
		 * .withUser(User.withUsername("santosh").password("iamsantosh").roles("USER"))
		 * .withUser(User.withUsername("kumar").password("iamsantosh").roles("ADMIN"));
		 */
		
		// with your own database
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username, password, enabled "+ "from users "+ "where username = ?")
		.authoritiesByUsernameQuery("select username, authority as role "+" from authorities "+"where username= ?");
	}
	
	@Bean
	public PasswordEncoder getPassEnc() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/user").hasAnyRole("USER","ADMIN")
		.antMatchers("/").permitAll()
		.and().formLogin();
	}
}
