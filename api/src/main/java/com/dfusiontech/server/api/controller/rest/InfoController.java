package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.rest.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InfoController {

	static final String CONTROLLER_URI = "/api/rest/info";
	private static final String API_SIGNATURE = "20180228";
	private static final String API_BUILD_VERSION = "ANPR, v. 0.1.5";

	@Autowired
	private ApplicationProperties applicationProperties;


	/**
	 * Get API version
	 *
	 * @return build version
	 */
	// @ApiOperation(value = "Get API version")
	@Produces(MediaType.TEXT_PLAIN)
	@RequestMapping(method = RequestMethod.GET, value = "/version", name = "Get API version")
	public String getAPIVersion() {
		return API_BUILD_VERSION;
	}

	/**
	 * Get API version signature
	 *
	 * @return signature
	 */
	// @ApiOperation(value = "Get API signature")
	@Produces(MediaType.TEXT_PLAIN)
	@RequestMapping(method = RequestMethod.GET, value = "/signature")
	public String getAPISignature() {
		return API_SIGNATURE;
	}
}
