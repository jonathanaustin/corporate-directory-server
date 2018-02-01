package com.github.bordertech.flux.actioncreator.impl;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.actioncreator.DataApiActionCreator;

/**
 * Default DATA API Action Creator.
 *
 * @param <D> the data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDataApiActionCreator<D extends DataApi> extends DefaultActionCreator implements DataApiActionCreator {

	private final D api;

	/**
	 * @param key the action creator key
	 * @param api the data API for actions
	 */
	public DefaultDataApiActionCreator(final String key, final D api) {
		super(key);
		this.api = api;
	}

	@Override
	public D getDataApi() {
		return api;
	}

}
