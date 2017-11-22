package com.github.bordertech.locator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Basic Factory class to load the Binding config classes.
 *
 * @author jonathan
 */
public class BindingFactory {

	private static final Log LOG = LogFactory.getLog(BindingFactory.class);

	private static final String PREFIX = "bordertech.binding.impl.";

	private static final String FILE_NAME = "bordertech-bindings.properties";

	private static final Configuration CONFIG;

	static {
		try {
			CONFIG = new PropertiesConfiguration(FILE_NAME);
		} catch (Exception e) {
			throw new IllegalStateException("Could not load config file for binding [" + FILE_NAME + "]." + e.getMessage(), e);
		}
	}

	private BindingFactory() {
	}

	/**
	 * Given an interface, instantiate a class implementing that interface.
	 *
	 * The classname to instantiate is obtained by looking in the runtime {@link Config configuration}, under the
	 * bordertech.factory.impl.&lt;interface name&gt; key.
	 *
	 * @param <T> the interface type.
	 * @param interfaz the interface to instantiate an implementation for.
	 * @return an Object which implements the given interface.
	 * @throws BindingException if no implementing class is registered in the {@link Config configuration}.
	 */
	public static <T> T newInstance(final Class<T> interfaz) {
		// Load class name form the properties file
		String key = interfaz.getName();
		return newInstance(key);
	}

	public static <T> T newInstance(final String key) {
		// Load class name form the properties file
		String paramKey = PREFIX + key;
		String classname = CONFIG.getString(paramKey);
		if (classname == null) {
			LOG.fatal("There needs to be a parameter defined for " + paramKey);
			throw new BindingException("There needs to be a parameter defined for " + paramKey);
		}

		// Create Instance
		try {
			Class<T> clazz = (Class<T>) Class.forName(classname.trim());
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BindingException("Failed to instantiate object of class " + classname, e);
		}
	}

}
