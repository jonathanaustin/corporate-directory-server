package com.github.bordertech.didums;

import java.io.Serializable;

/**
 * Didums binding config.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DidumsBinder extends Serializable {

	/**
	 * Configure the bindings.
	 *
	 * @param provider the provider to configure bindings on
	 */
	void configBindings(final DidumsProvider provider);
}
