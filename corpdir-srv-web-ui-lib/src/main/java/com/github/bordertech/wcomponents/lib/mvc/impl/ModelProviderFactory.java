package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.lib.mvc.ModelProvider;

/**
 * Model provider factory.
 *
 * @author jonathan
 */
public class ModelProviderFactory {

	private static final ModelProvider PROVIDER = new DefaultModelProvider();

	private ModelProviderFactory() {
	}

	public static ModelProvider getInstance() {
		return PROVIDER;
	}

}
