package com.java.hello;

import com.java.hello.resources.HelloWorldResource;
import com.java.hello.resources.HelloWorldResourceOne;
import com.java.hello.resources.RougeResource;

import be.tomcools.dropwizard.websocket.WebsocketBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloApplication extends Application<HelloConfiguration> {
	private WebsocketBundle websocket = new WebsocketBundle();

	public static void main(final String[] args) throws Exception {
		new HelloApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<HelloConfiguration> bootstrap) {
		super.initialize(bootstrap);
		bootstrap.addBundle(websocket);
	}

	@Override
	public void run(final HelloConfiguration configuration, final Environment environment) {

		websocket.addEndpoint(HelloWorldResource.class);
		environment.jersey().register(new RougeResource(configuration.getTemplate(), configuration.getDefaultName()));
		environment.jersey()
				.register(new HelloWorldResourceOne(configuration.getTemplate(), configuration.getDefaultName()));
	}

}
