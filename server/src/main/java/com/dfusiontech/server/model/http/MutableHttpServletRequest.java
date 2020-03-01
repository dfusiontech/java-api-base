package com.dfusiontech.server.model.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * Mutable HTTP Servlet Request
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2019-04-04
 */
public class MutableHttpServletRequest extends HttpServletRequestWrapper {

	private final Map<String, String> customHeaders;

	public MutableHttpServletRequest(HttpServletRequest request) {
		super(request);

		this.customHeaders = new HashMap<>();
	}

	public void setCustomHeader(String name, String value){
		this.customHeaders.put(name.toLowerCase(), value);
	}

	@Override
	public String getHeader(String name) {

		String lowercaseHeaderName = name.toLowerCase();

		// check the custom headers first
		String headerValue = customHeaders.get(lowercaseHeaderName);
		if (headerValue != null){
			return headerValue;
		}
		// else return from into the original wrapped object
		return ((HttpServletRequest) getRequest()).getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		// create a set of the custom header names
		Set<String> set = new HashSet<>(customHeaders.keySet());

		// now add the headers from the wrapped request object
		@SuppressWarnings("unchecked")
		Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
		while (e.hasMoreElements()) {
			// add the names of the request headers into the list
			String n = e.nextElement();
			set.add(n);
		}

		// create an enumeration from the set and return
		return Collections.enumeration(set);
	}


	@Override
	public Enumeration<String> getHeaders(String name) {
		List<String> values = Collections.list(super.getHeaders(name));
		if (customHeaders.containsKey(name.toLowerCase())) {
			values.add(customHeaders.get(name.toLowerCase()));
		}
		return Collections.enumeration(values);
	}

}
