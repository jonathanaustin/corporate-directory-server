package com.github.bordertech.corpdir.jersey.config.hk2;

import com.github.bordertech.corpdir.jersey.config.ApplicationServiceLocatorListener;
import com.github.bordertech.locator.LocatorConfig;
import org.glassfish.hk2.api.ServiceLocator;

/**
 *
 * @author jonathan
 */
public abstract class AbstractHk2LocatorConfig implements LocatorConfig {

	private final ServiceLocator serviceLocator;

	public AbstractHk2LocatorConfig(final String context) {
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
