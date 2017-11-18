package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author jonathan
 */
public class StoreUtil {

	private StoreUtil() {
	}

	public static <T extends Store> T getStore(final String key) {
		return (T) DispatcherFactory.getInstance().getStore(key);
	}

	/**
	 * @param <T> the bean value type
	 * @param store the store
	 * @param beanProperty the bean property to retrieve a value
	 * @return the bean value
	 *
	 */
	public static <T> T getStoreBeanValue(final Store store, final String beanProperty) {

		if (beanProperty == null || ".".equals(beanProperty)) {
			throw new IllegalStateException("Cannot have a null or dot bean property.");
		}
		try {
			T beanValue = (T) PropertyUtils.getProperty(store, beanProperty);
			return beanValue;
		} catch (Exception e) {
			throw new IllegalStateException("Failed to read store bean property " + beanProperty, e);
		}
	}

}
