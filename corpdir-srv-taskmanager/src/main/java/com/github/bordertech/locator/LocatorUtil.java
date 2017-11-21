package com.github.bordertech.locator;

/**
 * Locator Config UTIL.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class LocatorUtil {

	// Use a very basic factory to load the CDI provider
	private static final LocatorConfig PROVIDER = BindingFactory.newInstance(LocatorConfig.class);

	private static boolean configured;

	private LocatorUtil() {
	}

	public static void configBindings() {
		if (!configured) {
			PROVIDER.configBinding();
			configured = true;
		}
	}

}
