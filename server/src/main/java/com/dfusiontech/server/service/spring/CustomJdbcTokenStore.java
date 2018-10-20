package com.dfusiontech.server.service.spring;

import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Improving JDBC Store from Spring
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-20
 */
public class CustomJdbcTokenStore extends JdbcTokenStore {
	public CustomJdbcTokenStore(DataSource dataSource) {
		super(dataSource);
	}
}
