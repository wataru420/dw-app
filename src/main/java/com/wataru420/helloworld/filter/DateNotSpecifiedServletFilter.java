package com.wataru420.helloworld.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.eclipse.jetty.http.HttpStatus;

public class DateNotSpecifiedServletFilter implements javax.servlet.Filter {
    // Other methods in interface ommited for brevity

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	System.out.println("DateNotSpecifiedServletFilter");
        if (request instanceof HttpServletRequest) {
            String dateHeader = ((HttpServletRequest) request).getHeader(HttpHeaders.DATE);

            if (dateHeader == null) {
                chain.doFilter(request, response); // This signals that the request should pass this filter
            } else {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpStatus.BAD_REQUEST_400);
                httpResponse.getWriter().print("Date Header was not specified");
            }
        }
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}