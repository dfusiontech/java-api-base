package com.dfusiontech.server.service.spring;

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

		User result = new User(userName, "password", Collections.EMPTY_LIST);

		return result;
	}
}
