package com.dfusiontech.server.config;

import com.dfusiontech.server.service.spring.CustomJdbcUserDetailsManager;
import com.dfusiontech.server.service.spring.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

/**
 * Base Spring Application Configuration.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-25
 */
@Configuration
public class CoreSecurityConfig {

	/**
	 * Include DataSource depandency
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * PasswordEncoder Bean definition. Used to encode/match passwords.
	 *
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {

		/**
		 * Create DEFAULT BCRYPT Password Encoder from Spring Security
		 */
		return new BCryptPasswordEncoder();
	}

	/**
	 * Jdbc User Details Manager definition.
	 *
	 * @return
	 */
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		return new CustomJdbcUserDetailsManager();
	}

	/**
	 * Since Spring Security 2.0.6 we must instantiate UserDetailsService for oAuth manually
	 *
	 * @return
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	/**
	 * Token Store Implementation
	 *
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

}
