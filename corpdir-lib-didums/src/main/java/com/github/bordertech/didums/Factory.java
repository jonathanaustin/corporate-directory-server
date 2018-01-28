package com.github.bordertech.didums;

import com.github.bordertech.config.Config;
import java.util.ArrayList;
import java.util.List;
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

	public static <T> T newInstance(final Class<T> contract) {
		return newInstance(contract, null);
	}

	public static <T, U extends T> T newInstance(final Class<T> contract, final Class<U> defaultImpl) {
		return newInstance(contract.getName(), defaultImpl);
	}

	public static <T> T newInstance(final String key) {
		return newInstance(key, null);

	}

	public static <T> T newInstance(final String key, final Class<T> defaultImpl) {
		String implClassName = getImplClassName(key);
		Class<T> clazz;
		if (implClassName == null) {
			if (defaultImpl == null) {
				String paramKey = getParamKey(key);
				LOG.fatal("There needs to be a parameter defined for " + paramKey);
				throw new FactoryException("There needs to be a parameter defined for " + paramKey);
			}
			clazz = defaultImpl;
		} else {
			clazz = findClass(implClassName);
		}
		return createInstance(clazz);
	}

	public static <T> List<T> newMultiInstances(final Class<T> contract) {
		String[] classNames = getMultiImplClassName(contract.getName());
		List<T> impls = new ArrayList<>();
		for (String className : classNames) {
			Class<T> clazz = findClass(className);
			impls.add(createInstance(clazz));
		}
		return impls;
	}

	public static boolean hasImplementation(final Class contract) {
		return hasImplementation(contract.getName());
	}

	public static boolean hasImplementation(final String key) {
		return getImplClassName(key) != null;
	}

	private static <T> Class<T> findClass(final String className) {
		try {
			return (Class<T>) Class.forName(className.trim());
		} catch (ClassNotFoundException e) {
			throw new FactoryException("Class [" + className + "] not found.", e);
		}
	}

	private static <T> T createInstance(final Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			throw new FactoryException("Failed to instantiate object of class " + clazz.getName(), e);
		}
	}

	private static String getImplClassName(final String key) {
		String paramKey = getParamKey(key);
		return Config.getInstance().getString(paramKey);
	}

	private static String[] getMultiImplClassName(final String key) {
		String paramKey = getParamKey(key);
		return Config.getInstance().getStringArray(paramKey);
	}

	private static String getParamKey(final String key) {
		return PREFIX + key;
	}

}
