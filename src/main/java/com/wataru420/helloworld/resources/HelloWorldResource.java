package com.wataru420.helloworld.resources;

import io.dropwizard.jersey.caching.CacheControl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.wataru420.helloworld.core.Saying;
import com.wataru420.helloworld.core.Template;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HelloWorldResource.class);

	private final Template template;
	private final AtomicLong counter;

	public HelloWorldResource(Template template) {
		this.template = template;
		this.counter = new AtomicLong();
	}

	@GET
	@Timed(name = "get-requests")
	@CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
	public Saying sayHello(@QueryParam("name") Optional<String> name) {
		return new Saying(counter.incrementAndGet(), template.render(name));
	}

	@POST
	public void receiveHello(@Valid Saying saying) {
		LOGGER.info("Received a saying: {}", saying);
	}
}
