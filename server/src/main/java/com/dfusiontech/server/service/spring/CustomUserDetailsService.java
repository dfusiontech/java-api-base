package com.dfusiontech.server.service.spring;

import com.dfusiontech.server.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

/**
 * Customized implementation of User Details Service for Spring Security
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-20
 */
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	public CustomUserDetailsService() {
		log.info("#### CustomUserDetailsService. Initialize.");
	}

	/**
	 * Load User Details by its Primary username
	 *
	 * @param userName
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		// We must recreate User Properly. Like it is done in Spring.
		CustomJdbcUserDetailsManager userDetailsManager = BeanUtil.getBean("customJdbcUserDetailsManager", CustomJdbcUserDetailsManager.class);
		UserDetails userDetails = userDetailsManager.loadUserByUsername(userName);

		return userDetails;
	}
}
