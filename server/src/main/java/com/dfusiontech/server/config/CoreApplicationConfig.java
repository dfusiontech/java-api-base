package com.dfusiontech.server.config;

import com.dfusiontech.server.rest.ApplicationProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Base Spring Application Configuration.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-17
 */
@Configuration
@ComponentScan(basePackages = "com.dfusiontech")
@EnableAutoConfiguration
// @PropertySource(name = "messages", value = {"classpath:i18n/messages.properties"})
public class CoreApplicationConfig {

	/**
	 * Create base Dispatcher Servlet
	 *
	 * @return
	 */
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	/**
	 * Create Application Properties Bean
	 *
	 * @return
	 */
	@Bean
	public ApplicationProperties applicationProperties() {
		return new ApplicationProperties();
	}

	/**
	 * Config for rest template
	 * @param builder
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
