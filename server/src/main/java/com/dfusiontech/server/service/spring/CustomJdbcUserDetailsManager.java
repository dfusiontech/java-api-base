package com.dfusiontech.server.service.spring;

import com.dfusiontech.server.model.auth.UserDetailsImpl;
import com.dfusiontech.server.repository.jpa.RoleRepository;
import com.dfusiontech.server.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Define Jdbc User Details Manager based on Spring JdbcUserDetailsManager.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-26
 */
@Component
public class CustomJdbcUserDetailsManager extends JdbcUserDetailsManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private DataSource dataSource;

	/**
	 * Setup Data Source in post construction.
	 */
	@PostConstruct
	public void setup() {
		super.setDataSource(dataSource);
	}

	/**
	 * Load users by its username to get proper login info.
	 *
	 * @param username
	 * @return
	 */
	@Override
	protected List<UserDetails> loadUsersByUsername(final String username) {

		List<UserDetails> result = userRepository.findByEmailIsLike(username).stream().map(UserDetailsImpl::new).collect(Collectors.toList());

		return result;
	}

	/**
	 * Load List of User Roles (Authorities)
	 *
	 * @param username
	 * @return
	 */
	@Override
	protected List<GrantedAuthority> loadUserAuthorities(String username) {
		List<GrantedAuthority> result = roleRepository.getUserRolesByEmail(username).stream()
			.map(rolesEntity -> new SimpleGrantedAuthority(rolesEntity.getName()))
			.collect(Collectors.toList());

		return result;
	}

	/**
	 * Get custom user details
	 *
	 * @param username
	 * @param userFromUserQuery
	 * @param combinedAuthorities
	 * @return
	 */
	@Override
	protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
		// super.createUserDetails(username, userFromUserQuery, combinedAuthorities);

		UserDetailsImpl userDetails = new UserDetailsImpl(userFromUserQuery, combinedAuthorities, ((UserDetailsImpl) userFromUserQuery).getUserId());

		return userDetails;
	}
}
