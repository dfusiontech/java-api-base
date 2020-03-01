package com.dfusiontech.server.filter;

import com.dfusiontech.server.model.http.MutableHttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Process Authorization request to apply additional request headers
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2019-04-04
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class DownloadAuthorizationFilter implements Filter {

    /**
     * Filter a request.
     *
     * @param req   the request
     * @param res   the response
     * @param chain the filter chain
     * @throws IOException      throws i/o exceptions
     * @throws ServletException throws servlet exceptions
     */
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        final String method = request.getMethod();

        String requestAuthParameter = request.getParameter("token");
        if (StringUtils.isNotEmpty(requestAuthParameter)) {
			MutableHttpServletRequest customRequest = new MutableHttpServletRequest(request);
			customRequest.setCustomHeader("authorization", "Bearer " + requestAuthParameter);
			chain.doFilter(customRequest, response);
		} else {
			chain.doFilter(request, response);
		}
    }

    /**
     * Destroy the filter.
     */
    @Override
    public void destroy() {
        // nothing to destroy
    }

    /**
     * Initialize the filter.
     *
     * @param filterConfig the filter configuration
     * @throws ServletException throws servlet exceptions
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        synchronized (log) {
        }
    }
}
