package com.dfusiontech.server.rest.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception to be treated as UNAUTHORIZED.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @since    2018-10-17
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
@AllArgsConstructor
public class NotAuthenticatedException extends ServerException {

	public NotAuthenticatedException(String message) {
		super(message, 401);
	}

	/**
	 * Full API exception constructor
	 *
	 * @param message
	 * @param code
	 */
	public NotAuthenticatedException(String message, int code) {
		super(message, code);
	}

}
