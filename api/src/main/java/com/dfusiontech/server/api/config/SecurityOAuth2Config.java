package com.dfusiontech.server.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Arrays;

/**
 * Spring Application OAuth2 Security Configuration.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@Configuration
@EnableAuthorizationServer
@Order(100)
public class SecurityOAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${application.oauth.client.id}")
	private String oauthClientId;

	@Value("${application.oauth.client.secret}")
	private String oauthClientSecret;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
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

				clientDetails.setClientId(oauthClientId);
				// clientDetails.setClientSecret("{noop}21827392bacff");
				clientDetails.setClientSecret(passwordEncoder.encode(oauthClientSecret));
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
		endpoints.tokenStore(tokenStore);
		endpoints.userDetailsService(userDetailsService);
	}

}
