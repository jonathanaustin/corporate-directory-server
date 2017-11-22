package com.github.bordertech.locator;

/**
 * Locator Config UTIL.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class LocatorConfigUtil {

	// Use a very basic factory to load the CDI provider
	private static final LocatorConfig CONFIG = BindingFactory.newInstance(LocatorConfig.class);

	private static boolean configured;

	private LocatorConfigUtil() {
	}

	public static void configure() {
		if (!configured) {
			CONFIG.configBinding();
			configured = true;
		}
	}

	public static <T> T getService(final Class<T> service) {
		return (T) CONFIG.getService(service);
	}

}
