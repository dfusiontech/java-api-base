package com.dfusiontech.server.rest.exception;


import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception to be treated as CONFLICT.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @since    2018-11-08
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
@NoArgsConstructor
public class ConflictException extends ServerException {

	public ConflictException(String message) {
		super(message, 409);
	}

	/**
	 * Full API exception constructor
	 *
	 * @param message
	 * @param code
	 */
	public ConflictException(String message, int code) {
		super(message, code);
	}

}
