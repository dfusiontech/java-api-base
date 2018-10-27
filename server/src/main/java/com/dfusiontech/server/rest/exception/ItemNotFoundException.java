package com.dfusiontech.server.rest.exception;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception to be treated as NOT_FOUND.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-27
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class ItemNotFoundException extends ServerException {

	public ItemNotFoundException(String message) {
		super(message);
	}

}
