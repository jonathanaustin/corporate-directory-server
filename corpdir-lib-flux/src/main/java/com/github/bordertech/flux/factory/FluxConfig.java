package com.github.bordertech.flux.factory;

import com.github.bordertech.locator.LocatorConfig;

/**
 *
 * @author jonathan
 */
public interface FluxConfig extends LocatorConfig {

	void configFlux();

	void configDataApi();

	void configStores();

	void configActionCreactors();

}
