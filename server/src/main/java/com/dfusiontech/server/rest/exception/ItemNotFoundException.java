package com.dfusiontech.server.rest.exception;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception to be treated as NOT_FOUND.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @since    2018-10-27
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class ItemNotFoundException extends ServerException {

	public ItemNotFoundException(String message) {
		super(message, 404);
	}

	/**
	 * Full API exception constructor
	 *
	 * @param message
	 * @param code
	 */
	public ItemNotFoundException(String message, int code) {
		super(message, code);
	}

	/**
	 * Code API exception constructor
	 *
	 * @param code
	 */
	public ItemNotFoundException(int code) {
		super("Item not found", code);
	}

}
