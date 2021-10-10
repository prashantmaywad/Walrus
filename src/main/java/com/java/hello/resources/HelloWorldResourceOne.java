package com.java.hello.resources;

import com.java.hello.api.Backoff;
import com.java.hello.api.Saying;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResourceOne {

	final static Logger logger = Logger.getLogger(HelloWorldResourceOne.class);

	public HelloWorldResourceOne(String template, String defaultName) {

	}

	@GET
	@Timed
	public static Saying sayHello() {
		final String uri = "http://localhost:8080/rouge";
		Client client = ClientBuilder.newClient();
		Response res = client.target(uri).request().get();
		int status = res.getStatus();
		HelloWorldResource.send(Integer.toString(status));
		if (status == 200) {
			return new Saying("hello");
		} else {
			Backoff backoff = new Backoff();
			boolean flag = false;
			while (backoff.shouldRetry()) {
				res = client.target(uri).request().get();
				status = res.getStatus();
				if (status == 200) {
					backoff.doNotRetry();
					flag = true;
					break;
				} else {
					HelloWorldResource.send(Integer.toString(status));
					logger.info("Failed");
					backoff.errorOccured();
				}
			}
			if (flag)
				HelloWorldResource.send(Integer.toString(status));
			else {
				HelloWorldResource.send("Some technical Problem");
				return new Saying("Some technical Problem");
			}
		}
		return new Saying("");
	}

}
