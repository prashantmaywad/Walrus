package com.java.hello.resources;

import javax.ws.rs.core.Response;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/rouge")
@Produces(MediaType.APPLICATION_JSON)
public class RougeResource {
	public RougeResource(String template, String defaultName) {
	}

	@GET
	@Timed
	public Response sayHello(@QueryParam("name") Optional<String> name) {
		double random = Math.random();
		if (random <= 0.4) {
			return Response.status(Response.Status.OK).build();
		} else if (random <= 0.7) {
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
		} else {
			return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
		}
	}
}