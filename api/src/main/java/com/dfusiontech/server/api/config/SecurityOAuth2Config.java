package com.dfusiontech.server.api.config;

import com.dfusiontech.server.service.spring.CustomTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

/**
 * Spring Application OAuth2 Security Configuration.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@Configuration
@EnableAuthorizationServer
public class SecurityOAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		// oauthServer.allowFormAuthenticationForClients();
	}

	/**
	 * Configure Client Details service
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		ClientDetailsService clientDetailsService = new ClientDetailsService() {
			@Override
			public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
				BaseClientDetails clientDetails = new BaseClientDetails();

				clientDetails.setClientId("com.vrisk");
				clientDetails.setClientSecret("{noop}21827392bacff");
				clientDetails.setAccessTokenValiditySeconds(3600); // Access token to live an hour
				clientDetails.setRefreshTokenValiditySeconds(2592000); // Refresh token to be valid for 30 days
				clientDetails.setScope(Arrays.asList("read", "write")); // Scope related to resource server
				clientDetails.setAuthorizedGrantTypes(Arrays.asList("password", "refresh_token")); // grant type
				clientDetails.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("USER"))); // granted roles

				return clientDetails;
			}
		};

		clients.withClientDetails(clientDetailsService);
	}

	/**
	 * Configure Tokens managers and Endpoints
	 *
	 * @param endpoints
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		endpoints.tokenStore(tokenStore());
		endpoints.userDetailsService(userDetailsService);
	}

	/**
	 * Create Token Services
	 *
	 * @return
	 */
	/*
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {

		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setReuseRefreshToken(true);
		tokenServices.setTokenStore(tokenStore());

		return tokenServices;
	}
	*/

}
