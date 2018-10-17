package com.dfusiontech.server.api.config.swagger;


import com.dfusiontech.server.rest.ApplicationProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource(name = "swagger", value = "classpath:swagger.properties")
public class SwaggerConfig extends WebMvcConfigurerAdapter {

	private final ApplicationProperties properties;

	@Autowired
	public SwaggerConfig(ApplicationProperties properties) {
		this.properties = properties;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * Creates swagger configuration.
	 *
	 * @return swagger configuration docket
	 */
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.dfusiontech.server.api.controller"))
			.paths(PathSelectors.any())
			.build()
			.pathMapping("/")
			.directModelSubstitute(DateTime.class, String.class)
			.directModelSubstitute(Enum.class, String.class)
			.apiInfo(
				new ApiInfoBuilder()
					.title("Core API")
					.description("Core API documentation")
					.version(properties.getBuildVersion())
					.contact(new Contact("Eugene A. Kalosha", "https://dfusiontech.com", "ekalosha@dfusiontech.com"))
					.build()
			);
	}


	@Bean
	public SwaggerMapperConfig swaggerMapper() {
		return new SwaggerMapperConfig();
	}

	@Bean
	public SecurityConfiguration securityConfiguration() {
		return new SwaggerSecurityConfig(HttpHeaders.AUTHORIZATION);
	}

}
