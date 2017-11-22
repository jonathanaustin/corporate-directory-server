package com.github.bordertech.corpdir.jersey.config.hk2;

import com.github.bordertech.corpdir.jersey.config.hk2.binders.FluxBinder;
import com.github.bordertech.corpdir.jersey.config.hk2.binders.TaskManagerBinder;
import com.github.bordertech.flux.factory.FluxConfig;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

/**
 *
 * @author jonathan
 */
public class CorpDirConfig extends AbstractHk2LocatorConfig implements FluxConfig {

	public CorpDirConfig() {
		super(null);
	}

	@Override
	public void configBinding() {
		configFlux();
		configActionCreactors();
		configStores();
		configDataApi();
	}

	@Override
	public void configFlux() {
		ServiceLocatorUtilities.bind(getServiceLocator(), new TaskManagerBinder());
		ServiceLocatorUtilities.bind(getServiceLocator(), new FluxBinder());
	}

	@Override
	public void configDataApi() {
	}

	@Override
	public void configStores() {
	}

	@Override
	public void configActionCreactors() {
	}

}
