package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.rest.ApplicationProperties;
import com.dfusiontech.server.rest.exception.ApplicationExceptionCodes;
import com.dfusiontech.server.rest.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Example of controller. Returns Simple INFO
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-27
 */
@RestController
@RequestMapping(
	value = InfoController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Common Info"
)
@Slf4j
public class InfoController {

	static final String CONTROLLER_URI = "/api/info";

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


	/**
	 * Get Application API Errors
	 *
	 * @return signature
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/error-codes", produces = MediaType.APPLICATION_JSON)
	public Map<String, Object> getApplicationErrorCodes() {
		Map<String, Object> result;

		result = Arrays.stream(ApplicationExceptionCodes.class.getDeclaredFields())
			// filter out the non-static fields
			.filter(field -> Modifier.isStatic(field.getModifiers()))
			.collect(Collectors.toMap(Field::getName, field -> {
				try {
					return field.getInt(null);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return null;
			}));

		return result;
	}

	/**
	 * Get Application API Error
	 *
	 * @return signature
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/error/test/{errorCode}", produces = MediaType.APPLICATION_JSON)
	public Map<String, Object> testErrorCode(@PathVariable("errorCode") @NotNull @Size(min = 1) int errorCode) {
		throw new BadRequestException("Server error message [726]", errorCode);
	}

}
