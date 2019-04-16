package com.dfusiontech.server.api.config;

import com.dfusiontech.server.filter.CORSPermissiveFilter;
import com.dfusiontech.server.filter.DownloadAuthorizationFilter;
import com.dfusiontech.server.filter.JsonToUrlEncodedAuthenticationFilter;
import com.dfusiontech.server.model.jpa.domains.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableResourceServer
@Order(103)
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	JsonToUrlEncodedAuthenticationFilter jsonFilter;


	/**
	 * Configure OAuth server security configuration
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		// Adding Permissive CORS Filter to allow JS cross origin
		http.addFilterBefore(new CORSPermissiveFilter(), ChannelProcessingFilter.class);
		http.addFilterBefore(new DownloadAuthorizationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
		http.addFilterAfter(jsonFilter, BasicAuthenticationFilter.class);

		http.authorizeRequests().antMatchers("/api/info/**", "/api/anonymous/**").permitAll();
		http.authorizeRequests().antMatchers("/api/logout").authenticated();
		http.authorizeRequests().antMatchers("/api/admin/**").hasAnyAuthority(RoleType.ADMIN.role());
		http.authorizeRequests().antMatchers("/api/users/self", "/api/users/filter").authenticated();
		http.authorizeRequests().antMatchers("/api/users", "/api/users/**").hasAnyAuthority(RoleType.ADMIN.role());
		// http.authorizeRequests().antMatchers("/api/roles", "/api/roles/**").hasAuthority("ADMIN");

		http.requestMatchers().antMatchers("/api/**")
			.and()
			.authorizeRequests()
			.antMatchers("/api/**").authenticated();

	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);

		// resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
	}
}
