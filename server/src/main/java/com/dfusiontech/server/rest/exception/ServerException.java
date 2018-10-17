package com.dfusiontech.server.rest.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * General exception to be treated as SERVER_EXCEPTION.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-17
 */
@Setter
@Getter
@NoArgsConstructor
public class ServerException extends RuntimeException {

	public ServerException(String message) {
		super(message);
	}

}
