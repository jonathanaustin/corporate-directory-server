package com.github.bordertech.corpdir.jersey.config;

import com.github.bordertech.corpdir.web.api.servlet.CorsResponseFilter;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {
		register(CorsResponseFilter.class);
		register(new ApplicationBinder());

		packages(true, "com.github.bordertech.corpdir.web.api.resource");
	}
}
