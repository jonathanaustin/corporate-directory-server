package com.github.bordertech.corpdir.jersey.config.hk2;

import com.github.bordertech.didums.hk2.DidumsHk2Provider;

/**
 * Didums HK2 provider that uses Jersey Service Locator.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DidumsJerseyHk2Provider extends DidumsHk2Provider {

	public DidumsJerseyHk2Provider() {
		// Uses a Container Listener to get Jersey's ServiceLocator.
		super(ApplicationServiceLocatorListener.getServiceLocator());
	}

}
