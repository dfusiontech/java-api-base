
package com.dfusiontech.server.api;

import com.dfusiontech.server.repository.jpa.core.CoreRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * SpringBoot Application starter class
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, JacksonAutoConfiguration.class})
@ComponentScan(basePackages = {"com.dfusiontech"})
@EnableJpaRepositories(basePackages = {"com.dfusiontech.server.repository"}, repositoryBaseClass = CoreRepositoryImpl.class)
@EntityScan(basePackages = {"com.kpsys.server.model"})
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@Slf4j
public class APIRestApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(APIRestApplication.class);


	/**
	 * SpringBoot Starter method
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		/**
		 * Initializing Spring Boot Application
		 */
		final SpringApplication springApplication = new SpringApplication(APIRestApplication.class);

		/**
		 * Get Current Environment
		 */
		ConfigurableApplicationContext applicationContext = springApplication.run(args);
		final Environment environment = applicationContext.getEnvironment();

		showStartupMessages(environment);
	}

	/**
	 * Show startup messages for Spring Application
	 *
	 * @param environment
	 */
	private static void showStartupMessages(Environment environment) {
		String applicationHost = "localhost";

		try {
			applicationHost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException exception) {
			;
		}

		String logoString = "\n\n\n\n" +
			" \n" +
			"    dMMMMb  .aMMMb  dMMMMb  dMP dMP dMP dMMMMb  .aMMMMP       .aMMMMP dMP dMP dMMMMb  dMP dMP        .aMMMb  dMMMMb  dMMMMb  dMMMMb \n" +
			"   dMP.dMP dMP\"dMP dMP.dMP dMP.dMP amr dMP dMP dMP\"          dMP\"    dMP dMP dMP.dMP dMP dMP        dMP\"dMP dMP dMP dMP.dMP dMP.dMP \n" +
			"  dMMMMP\" dMMMMMP dMMMMK\" dMMMMK\" dMP dMP dMP dMP MMP\"      dMP MMP\"dMP dMP dMMMMK\" dMP dMP        dMMMMMP dMP dMP dMMMMP\" dMMMMK\"  \n" +
			" dMP     dMP dMP dMP\"AMF dMP\"AMF dMP dMP dMP dMP.dMP       dMP.dMP dMP.aMP dMP\"AMF dMP.aMP        dMP dMP dMP dMP dMP     dMP\"AMF   \n" +
			"dMP     dMP dMP dMP dMP dMP dMP dMP dMP dMP  VMMMP\"        VMMMP\"  VMMMP\" dMP dMP  VMMMP\"        dMP dMP dMP dMP dMP     dMP dMP    \n" +
			" \n" +
			"\t\t{}, v.-{}\n" +
			"============================================================================================= \n" +
			" Startup Info:\n" +
			" Local: \thttp://127.0.0.1:{}\n" +
			" External: \thttp://{}:{}\n" +
			" Profiles: \t{}\n" +
			" Database: \t{}\n" +
			"============================================================================================= \n" +
			"\n";

		LOGGER.info(
			logoString,
			environment.getProperty("info.app.name"),
			environment.getProperty("info.app.version"),
			environment.getProperty("server.port"),
			applicationHost,
			environment.getProperty("server.port"),
			environment.getActiveProfiles(),
			environment.getProperty("datasource.url")
		);
	}

	/**
	 * Spring Application Builder and Configuration
	 *
	 * @param application
	 * @return
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		SpringApplicationBuilder applicationBuilder = application.sources(APIRestApplication.class);

		final SpringApplication springApplication = applicationBuilder.application();

		return applicationBuilder;
	}

	/**
	 * Creating Root Application context and get Application Environment
	 *
	 * @param servletContext
	 * @return
	 */
	@Override
	protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
		WebApplicationContext webApplicationContext = super.createRootApplicationContext(servletContext);

		showStartupMessages(webApplicationContext.getEnvironment());

		return webApplicationContext;
	}
}
