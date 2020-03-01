package com.dfusiontech.server.filter;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Security filter to apply support of JSON type Request.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-11-01
 */
@Component
public class JsonToUrlEncodedAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * Overriding Internal Filtering logic to handle JSON type Parameters in the Request
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		if (Objects.equals(request.getServletPath(), "/oauth/token") && Objects.equals(request.getContentType(), "application/json")) {

			// Obtain JSON String as ByteArray
			byte[] json = IOUtils.toByteArray(request.getInputStream());

			// Get Parameters list from JSON String
			Map<String, String> jsonMap = new ObjectMapper().readValue(json, Map.class);;
			Map<String, String[]> parameters =
				jsonMap.entrySet().stream()
					.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry ->  new String[]{entry.getValue()})
					);

			// Create RequestWrapper for Parameters and HTTP Request
			HttpServletRequest requestWrapper = new RequestWrapper(request, parameters);

			// Apply filtering
			filterChain.doFilter(requestWrapper, response);
		} else {
			// Skip filter and continue with the Filters Chain.
			filterChain.doFilter(request, response);
		}
	}

	/**
	 * Custom Request Wrapper for Security filter to apply JSON body type Request.
	 *
	 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
	 * @version  0.1.1
	 * @since    2018-11-01
	 */
	private class RequestWrapper extends HttpServletRequestWrapper {

		/**
		 * Request parameters map
		 */
		private final Map<String, String[]> params;

		/**
		 * Full parameters constructor
		 *
		 * @param request
		 * @param params
		 */
		RequestWrapper(HttpServletRequest request, Map<String, String[]> params) {
			super(request);
			this.params = params;
		}

		/**
		 * Get Parameter by its name
		 *
		 * @param name
		 * @return
		 */
		@Override
		public String getParameter(String name) {
			if (this.params.containsKey(name)) {
				return this.params.get(name)[0];
			}
			return "";
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return this.params;
		}

		/**
		 * Get list of all parameter names
		 *
		 * @return
		 */
		@Override
		public Enumeration<String> getParameterNames() {
			return new Enumerator<String>(params.keySet());
		}

		/**
		 * Get all values for some parameter
		 *
		 * @param name
		 * @return
		 */
		@Override
		public String[] getParameterValues(String name) {
			return params.get(name);
		}
	}
}
