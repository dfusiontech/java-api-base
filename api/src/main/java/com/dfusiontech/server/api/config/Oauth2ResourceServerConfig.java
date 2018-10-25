package com.dfusiontech.server.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@Order(103)
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	/**
	 * Configure OAuth server security configuration
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.requestMatchers().antMatchers("/api/**")
			.and()
			.authorizeRequests()
			.antMatchers("/api/**").authenticated();

	}
}
