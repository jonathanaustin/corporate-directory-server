package com.github.bordertech.corpdir.jersey.config.hk2;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

/**
 * A listener that makes the HK2 ServiceLocator used by Jersey available.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ApplicationServiceLocatorListener implements ContainerLifecycleListener {

	private static ServiceLocator serviceLocator;

	@Override
	public void onStartup(final Container container) {
		serviceLocator = container.getApplicationHandler().getServiceLocator();
	}

	@Override
	public void onReload(final Container container) {/*...*/
	}

	@Override
	public void onShutdown(final Container container) {/*...*/
	}

	public static ServiceLocator getServiceLocator() {
		if (serviceLocator == null) {
			throw new IllegalStateException("Jersey has not been started yet");
		}
		return serviceLocator;
	}

}
