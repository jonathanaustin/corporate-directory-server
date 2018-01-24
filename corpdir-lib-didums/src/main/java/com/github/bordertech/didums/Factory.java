package com.github.bordertech.didums;

import com.github.bordertech.config.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Basic Factory for contract implementations.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Factory {

	private static final Log LOG = LogFactory.getLog(Factory.class);

	private static final String PREFIX = "bordertech.factory.impl.";

	private Factory() {
	}

	/**
	 * Given an interface, instantiate a class implementing that interface.
	 *
	 * The classname to instantiate is obtained by looking in the runtime {@link Config configuration}, under the
	 * bordertech.factory.impl.&lt;interface name&gt; key.
	 *
	 * @param <T> the interface type.
	 * @param contract the interface to instantiate an implementation for.
	 * @return an Object which implements the given interface.
	 * @throws FactoryException if no implementing class is registered in the {@link Config configuration}.
	 */
	public static <T> T newInstance(final Class<T> contract) {
		return newInstance(contract.getName(), null);
	}

	/**
	 * Given an interface, instantiate a class implementing that interface.
	 *
	 * The classname to instantiate is obtained by looking in the runtime {@link Config configuration}, under the
	 * bordertech.factory.impl.&lt;interface name&gt; key.
	 *
	 * @param <T> the interface type.
	 * @param <U> the concrete default class
	 * @param contract the interface to instantiate an implementation for.
	 * @param defaultImpl the optional default implementation
	 * @return an Object which implements the given interface.
	 * @throws FactoryException if no implementing class is registered in the {@link Config configuration}.
	 */
	public static <T, U extends T> T newInstance(final Class<T> contract, final Class<U> defaultImpl) {
		return newInstance(contract.getName(), defaultImpl);
	}

	public static <T> T newInstance(final String key) {
		return newInstance(key, null);

	}

	public static <T> T newInstance(final String key, final Class<T> defaultImpl) {
		// Load implementation class name from the properties file
		String classname = getImplementationClassName(key);

		// Find class
		Class<T> clazz;
		if (classname == null) {
			if (defaultImpl == null) {
				String paramKey = getParamKey(key);
				LOG.fatal("There needs to be a parameter defined for " + paramKey);
				throw new FactoryException("There needs to be a parameter defined for " + paramKey);
			}
			clazz = defaultImpl;
		} else {
			try {
				clazz = (Class<T>) Class.forName(classname.trim());
			} catch (ClassNotFoundException e) {
				throw new FactoryException("Class [" + classname + "] not found.", e);
			}
		}

		// Create Instance
		try {
			return clazz.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			throw new FactoryException("Failed to instantiate object of class " + classname, e);
		}
	}

	public static boolean hasImplementation(final Class contract) {
		return hasImplementation(contract.getName());

	}

	public static boolean hasImplementation(final String key) {
		return getImplementationClassName(key) != null;
	}

	private static String getImplementationClassName(final String key) {
		return Config.getInstance().getString(getParamKey(key));
	}

	private static String getParamKey(final String key) {
		return PREFIX + key;
	}

}
