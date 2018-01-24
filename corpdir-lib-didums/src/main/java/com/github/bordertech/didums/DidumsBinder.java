package com.github.bordertech.didums;

import java.io.Serializable;

/**
 * Didums Binding Configs.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DidumsBinder extends Serializable {

	void configBindings(final DidumsProvider provider);
}
