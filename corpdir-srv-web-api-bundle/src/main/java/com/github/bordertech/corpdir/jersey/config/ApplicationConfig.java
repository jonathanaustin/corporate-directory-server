package com.github.bordertech.corpdir.jersey.config;

import com.github.bordertech.corpdir.web.api.servlet.AuthContainerRequestFilter;
import com.github.bordertech.corpdir.web.api.servlet.CorsResponseFilter;
import javax.ws.rs.Priorities;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {
		register(AuthContainerRequestFilter.class, Priorities.AUTHENTICATION);
		register(CorsResponseFilter.class);
		register(RolesAllowedDynamicFeature.class);
		register(new ApplicationBinder());
		packages(true, "com.github.bordertech.corpdir.web.api.resource");
	}
}
