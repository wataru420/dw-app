package com.wataru420.helloworld.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;

@PreMatching
public class PreMatchingFilter implements ContainerRequestFilter {
 
    @Override
    public void filter(ContainerRequestContext requestContext)
                        throws IOException {
        // change all PUT methods to POST
        if (requestContext.getMethod().equals("PUT")) {
            requestContext.setMethod("POST");
        }
        System.out.println("PreMatchingFilter");
    }
}
