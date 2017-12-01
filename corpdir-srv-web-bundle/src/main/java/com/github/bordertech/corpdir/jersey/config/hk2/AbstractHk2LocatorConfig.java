package com.github.bordertech.corpdir.jersey.config.hk2;

import com.github.bordertech.corpdir.jersey.config.ApplicationServiceLocatorListener;
import com.github.bordertech.locator.LocatorConfig;
import org.glassfish.hk2.api.ServiceLocator;

/**
 * LocatorConfig that uses HK2 for binding.
 *
 * @author jonathan
 */
public abstract class AbstractHk2LocatorConfig implements LocatorConfig {

	private final ServiceLocator serviceLocator;

	public AbstractHk2LocatorConfig(final String context) {
		// Uses a Container Listener to get Jersey's ServiceLocator.
		serviceLocator = ApplicationServiceLocatorListener.getServiceLocator();
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	@Override
	public Object getService(final Class service) {
		return serviceLocator.getService(service);
	}

}
