package com.wataru420.helloworld.filter;


import java.lang.reflect.Method;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.core.ExtendedUriInfo;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class DateNotSpecifiedFilter implements ContainerRequestFilter {

    //@Context ExtendedUriInfo extendedUriInfo;
    
    @Context ResourceInfo resourceInfo;

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        //boolean methodNeedsDateHeader = extendedUriInfo.getMatchedMethod().isAnnotationPresent(DateRequired.class);
    	//Method method = resourceInfo.getResourceMethod();
    	//System.out.println(method);
    	boolean methodNeedsDateHeader = true;//method.isAnnotationPresent(DateRequired.class);
        String dateHeader = request.getHeaderValue("X-test");
        System.out.println("DateNotSpecifiedFilter:" + dateHeader);
        System.out.println(request.getRequestHeaders());

        if (methodNeedsDateHeader && dateHeader == null) {
            Exception cause = new IllegalArgumentException("Date Header was not specified");
            throw new WebApplicationException(cause, Response.Status.BAD_REQUEST);
        } else {
            return request;
        }
    }
}
