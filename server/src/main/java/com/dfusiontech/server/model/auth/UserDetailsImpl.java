package com.dfusiontech.server.model.auth;

import com.dfusiontech.server.model.jpa.entity.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Default Spring user abstraction
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-26
 */
public class UserDetailsImpl extends User {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	@Getter
	private Long userId;

	/**
	 * Fully parametrized constructor
	 *
	 * @param userId
	 * @param username
	 * @param password
	 * @param enabled
	 * @param authorities
	 */
	public UserDetailsImpl(Long userId, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, true, true, true, authorities);

		this.userId = userId;
	}

	public UserDetailsImpl(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities, Long userId) {
		this(userId, userDetails.getUsername(), userDetails.getPassword(), userDetails.isEnabled(), authorities);
	}

	/**
	 * Default User Entity constructor
	 *
	 * @param user
	 */
	public UserDetailsImpl(Users user) {
		this(Long.valueOf(user.getId()), user.getEmail(), user.getPassword(), user.getEnabled(), AuthorityUtils.NO_AUTHORITIES);
	}

}
