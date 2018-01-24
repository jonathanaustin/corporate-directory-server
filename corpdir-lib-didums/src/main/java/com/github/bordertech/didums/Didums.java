package com.github.bordertech.didums;

import java.lang.annotation.Annotation;

/**
 * DI Helper.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Didums {

	private static final DidumsProvider PROVIDER;

	static {
		PROVIDER = Factory.newInstance(DidumsProvider.class, DefaultDidumsProvider.class);
		if (Factory.hasImplementation(DidumsBinder.class)) {
			DidumsBinder binder = Factory.newInstance(DidumsBinder.class);
			binder.configBindings(PROVIDER);
		}
	}

	private Didums() {
	}

	public static <T> boolean hasService(final Class<T> service, final Annotation... qualifiers) {
		// Provider
		T impl = PROVIDER.getService(service, qualifiers);
		if (impl != null) {
			return true;
		}
		// Fallback to basic factory
		return qualifiers.length == 0 ? Factory.hasImplementation(service) : false;
	}

	public static <T> T getService(final Class<T> service, final Annotation... qualifiers) {
		// Provider
		T impl = PROVIDER.getService(service, qualifiers);
		// Fallback to basic factory
		if (impl == null) {
			if (qualifiers.length > 0) {
				throw new FactoryException("No implementation available for service [" + service.getName() + "] with qualifiers.");
			}
			impl = Factory.newInstance(service);
		}
		return impl;
	}

	public static <T> T createAndInject(final Class<T> createMe) {
		return PROVIDER.createAndInject(createMe);
	}

	public static <T, U extends T> void bind(final Class<T> contract, final Class<U> contractImpl, final Annotation... qualifiers) {
		PROVIDER.bind(contract, contractImpl, true, qualifiers);
	}

	public static <T, U extends T> void bind(final Class<T> contract, final Class<U> contractImpl, final boolean singleton, final Annotation... qualifiers) {
		PROVIDER.bind(contract, contractImpl, singleton, qualifiers);
	}

}
