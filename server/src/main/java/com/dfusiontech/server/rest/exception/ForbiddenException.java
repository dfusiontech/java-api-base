package com.dfusiontech.server.rest.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception to be treated as FORBIDDEN.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @since    2018-12-19
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
@AllArgsConstructor
public class ForbiddenException extends ServerException {

	public ForbiddenException(String message) {
		super(message, 403);
	}

	/**
	 * Full API exception constructor
	 *
	 * @param message
	 * @param code
	 */
	public ForbiddenException(String message, int code) {
		super(message, code);
	}

}
