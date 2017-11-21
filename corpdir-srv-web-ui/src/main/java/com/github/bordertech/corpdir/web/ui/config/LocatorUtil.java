package com.github.bordertech.corpdir.web.ui.config;

import com.github.bordertech.corpdir.jersey.config.ApplicationBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

/**
 *
 * @author jonathan
 */
public class LocatorUtil {

	private static final ServiceLocator LOCATOR = ServiceLocatorFactory.getInstance().create("ui-admin");

	static {
		ServiceLocatorUtilities.bind(LOCATOR, new ApplicationBinder());
		ServiceLocatorUtilities.bind(LOCATOR, new TaskManagerBinder());
	}

	private LocatorUtil() {
	}

	public static <T> T getService(final Class<T> service) {
		return LOCATOR.getService(service);
	}

}
