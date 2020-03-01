package com.dfusiontech.server.rest.exception;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception to be treated as INTERNAL_SERVER_ERROR.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @since    2018-12-25
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@AllArgsConstructor
public class InternalServerErrorException extends ServerException {

	public InternalServerErrorException(String message) {
		super(message, 500);
	}

	/**
	 * Full API exception constructor
	 *
	 * @param message
	 * @param code
	 */
	public InternalServerErrorException(String message, int code) {
		super(message, code);
	}

}
