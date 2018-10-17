package com.dfusiontech.server.rest.exception;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception to be treated as BAD_REQUEST.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@AllArgsConstructor
public class BadRequestException extends ServerException {

	public BadRequestException(String message) {
		super(message);
	}

}
