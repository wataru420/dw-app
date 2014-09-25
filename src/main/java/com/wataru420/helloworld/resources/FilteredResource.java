package com.wataru420.helloworld.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.wataru420.helloworld.filter.DateRequired;

@Path("/filtered")
public class FilteredResource {

	@GET
	@DateRequired
	@Path("hello")
	public String sayHello() {
		return "hello";
	}
}
