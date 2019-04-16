package com.dfusiontech.server.api.config;

import com.dfusiontech.server.rest.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API Exception Resolver for API
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2019-04-09
 */
@Component
@Slf4j
public class APIExceptionResolver extends AbstractHandlerExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object target, Exception exception) {
		if (exception instanceof ServerException) {

			ModelAndView model = new ModelAndView("Server Exception");
			model.addObject("requestUri", httpServletRequest.getRequestURI());
			// model.addObject("exception", exception);

			model.addObject("target", target);
			model.addObject("code", 1111);
			model.addObject("message", ((ServerException) exception).getMessage());

			// Setting status code
			httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			httpServletResponse.setHeader("Content-Type", "application/json; charset=utf-8");

			return model;
		}

		return null;
	}
}
