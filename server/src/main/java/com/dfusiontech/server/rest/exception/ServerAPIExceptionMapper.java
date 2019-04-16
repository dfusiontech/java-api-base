package com.dfusiontech.server.rest.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * General exception Mapper
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @since    2018-12-20
 */
@Component
@Provider
public class ServerAPIExceptionMapper implements ExceptionMapper<ServerException> {
	@Override
	public Response toResponse(ServerException exception) {

		Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

		if (exception instanceof ItemNotFoundException) {
			status = Response.Status.NOT_FOUND;
		} else if (exception instanceof BadRequestException) {
			status = Response.Status.BAD_REQUEST;
		} else if (exception instanceof ConflictException) {
			status = Response.Status.CONFLICT;
		} else if (exception instanceof ForbiddenException) {
			status = Response.Status.FORBIDDEN;
		} else if (exception instanceof NotAuthenticatedException) {
			status = Response.Status.UNAUTHORIZED;
		}

		return Response.status(status).entity(ClientErrorObject.of(exception)).build();
	}

	/**
	 * Client Error Bean
	 */
	@Data
	@Getter
	@Setter
	public static class ClientErrorObject {

		private int code;
		private String message;

		/**
		 * All arguments constructor
		 *
		 * @param code
		 * @param message
		 */
		public ClientErrorObject(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public static ClientErrorObject of(ServerException exception) {
			return null;
		}
	}
}
