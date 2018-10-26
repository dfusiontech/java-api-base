package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.rest.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(
	value = InfoController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Common Info"
)
// @Api(value = "Info", description = "Info about the platform.")
@Slf4j
public class InfoController {

	static final String CONTROLLER_URI = "/api/rest/info";

	@Value("${info.app.signature}")
	private String API_SIGNATURE;

	@Value("${info.app.version}")
	private String API_BUILD_VERSION;

	@Autowired
	private ApplicationProperties applicationProperties;


	/**
	 * Get API version
	 *
	 * @return build version
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/version", name = "Get API version", produces = MediaType.TEXT_PLAIN)
	public String getAPIVersion() {
		return API_BUILD_VERSION;
	}

	/**
	 * Get API version signature
	 *
	 * @return signature
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/signature", produces = MediaType.TEXT_PLAIN)
	public String getAPISignature() {
		return API_SIGNATURE;
	}
}
