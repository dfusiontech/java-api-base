package com.dfusiontech.server.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 * Spring Application Security Configuration.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@Configuration
// @EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class HybridSecurityConfig extends WebSecurityConfigurerAdapter {

	// private final PasswordEncoder passwordEncoder;

	@Autowired
	public HybridSecurityConfig() {
		// this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Configuring Authentication
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
	}

	/**
	 * Configuring HTTP Security for the Application
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.httpBasic().disable()
			.formLogin().disable();
	}
}
