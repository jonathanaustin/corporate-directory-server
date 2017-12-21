package com.github.bordertech.corpdir.jersey.config;

import com.github.bordertech.locator.LocatorUtil;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

/**
 * A listener that makes the ServiceLocator used by Jersey available.
 *
 * @author jonathan
 */
public class ApplicationServiceLocatorListener implements ContainerLifecycleListener {

	private static ServiceLocator serviceLocator;

	public static ServiceLocator getServiceLocator() {
		if (serviceLocator == null) {
			throw new IllegalStateException("Jersey has not been started yet");
		}
		return serviceLocator;
	}

	public void onStartup(Container container) {
		serviceLocator = container.getApplicationHandler().getServiceLocator();
		LocatorUtil.configure();
	}

	@Override
	public void onReload(final Container container) {/*...*/
	}

	@Override
	public void onShutdown(final Container container) {/*...*/
	}

}
