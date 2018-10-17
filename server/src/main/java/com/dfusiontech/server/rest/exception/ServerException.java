package com.dfusiontech.server.rest.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * General exception to be treated as SERVER_EXCEPTION.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@Setter
@Getter
@NoArgsConstructor
public class ServerException extends RuntimeException {

	public ServerException(String message) {
		super(message);
	}

}
