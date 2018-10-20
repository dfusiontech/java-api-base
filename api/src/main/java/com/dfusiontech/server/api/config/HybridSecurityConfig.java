package com.dfusiontech.server.api.config;

import com.dfusiontech.server.service.spring.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Spring Application Security Configuration.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@Configuration
// @EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class HybridSecurityConfig extends WebSecurityConfigurerAdapter {

	// private final PasswordEncoder passwordEncoder;

	@Autowired
	public HybridSecurityConfig() {
		// this.passwordEncoder = passwordEncoder;
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
	 * Since Spring Security 2.0.6 we must instantiate UserDetailsService for oAuth manually
	 *
	 * @return
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	/**
	 * Configuring Authentication
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Set In-Memory Authorization with Static Users
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
			.withUser("ekalosha@gmail.com").password("password").roles("USER")
			.and()
			.withUser("ekalosha2").password("password2").roles("USER")
			.and()
			.withUser("ekalosha3").password("password3").roles("USER")
			.and()
			.withUser("ekalosha4").password("password4").roles("USER")
			.and()
			.withUser("ekalosha5").password("password5").roles("USER");
	}

	/**
	 * Configuring HTTP Security for the Application
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable basic authorization
		http.csrf().disable();
		http.httpBasic().disable();
		http.formLogin().disable();

		// Disable Sessions for API Application
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).sessionFixation().none();

		// http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

		http
			.antMatcher("/**")
			.authorizeRequests()
			.antMatchers("/", "/login**", "/swagger**", "/swagger/**",
				"/webjars/springfox-swagger-ui/**", "/swagger-resources/**", "/csrf"
				, "/oauth/**"
			)
			.permitAll();
			// .anyRequest().authenticated();

		// http.authorizeRequests().antMatchers("/api/**").hasRole("USER");
			//.anyRequest().authenticated();

		// http.anonymous().disable();
	}

}
