package com.github.bordertech.locator;

import java.io.Serializable;

/**
 * Configure CDI Bindings.
 *
 * @author jonathan
 */
public interface LocatorConfig extends Serializable {

	void configBinding();

	Object getService(final Class service);

}
