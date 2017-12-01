package com.github.bordertech.corpdir.jersey.config;

import com.github.bordertech.corpdir.jersey.config.hk2.binders.ApplicationServicesBinder;
import com.github.bordertech.corpdir.web.api.servlet.AuthContainerRequestFilter;
import com.github.bordertech.corpdir.web.api.servlet.CorsResponseFilter;
import javax.ws.rs.Priorities;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Configure Jersey REST Services and the HK2 (ie CDI) for the WebApp.
 *
 * @author jonathan
 */
public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {
		register(AuthContainerRequestFilter.class, Priorities.AUTHENTICATION);
		register(CorsResponseFilter.class);
		register(RolesAllowedDynamicFeature.class);
		register(new ApplicationServicesBinder());
		// Listener to get the ServiceLocator Used by Jersey
		register(new ApplicationServiceLocatorListener());
		packages(true, "com.github.bordertech.corpdir.web.api.resource");
	}
}
