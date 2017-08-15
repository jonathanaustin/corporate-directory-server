package com.github.bordertech.wcomponents.lib.flux.impl;

import java.io.Serializable;

/**
 * Execute a service action.
 *
 * @author Jonathan Austin
 * @param <S> the criteria type
 * @param <T> the response type
 * @since 1.0.0
 */
public interface ExecuteService<S, T> extends Serializable {

	T executeService(final S criteria);

}
