package com.wataru420.helloworld.filter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class DateRequiredFeature implements DynamicFeature {
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
    	System.out.println("DateRequiredFeature");
        if (resourceInfo.getResourceMethod().getAnnotation(DateRequired.class) != null) {
        	System.out.println("DateRequiredFeature");
            context.register(DateNotSpecifiedFilter.class);
        }
    }
}
