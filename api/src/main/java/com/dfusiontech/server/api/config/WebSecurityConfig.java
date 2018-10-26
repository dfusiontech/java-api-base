package com.dfusiontech.server.api.config;

import com.dfusiontech.server.service.spring.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Spring Application Security Configuration.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	private final JdbcUserDetailsManager jdbcUserDetailsManager;

	/**
	 * Default Bean constructor
	 *
	 * @param jdbcUserDetailsManager
	 * @param passwordEncoder
	 */
	@Autowired
	public WebSecurityConfig(final JdbcUserDetailsManager jdbcUserDetailsManager, final PasswordEncoder passwordEncoder) {
		this.jdbcUserDetailsManager = jdbcUserDetailsManager;
		this.passwordEncoder = passwordEncoder;
	}


	/**
	 * Since Spring Security 2.0.6 we must expose AuthenticationManager for oAuth manually
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	/**
	 * Configuring Authentication
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Set Authorization with Database Users with BCrypt password encoder.
		auth.apply(new JdbcUserDetailsManagerConfigurer<>(jdbcUserDetailsManager)).passwordEncoder(passwordEncoder);

		// TODO Remove duplicates
		// auth.userDetailsService(userDetailsService());
	}

	/**
	 * Configuring HTTP Security for the Application
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable Sessions for API Application
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).sessionFixation().none();

		http
			.antMatcher("/**")
			.authorizeRequests()
			.antMatchers(
				"/",
				"/login**",
				"/swagger**",
				"/swagger/**",
				"/webjars/springfox-swagger-ui/**",
				"/swagger-resources/**",
				"/csrf",
				"/oauth/**"
			)
			.permitAll();

		// Allow actuator enpoints. eg: /health, /info etc.
		http.authorizeRequests().antMatchers("/actuator/**").permitAll();
	}

}
