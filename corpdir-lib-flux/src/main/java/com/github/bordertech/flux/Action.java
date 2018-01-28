package com.github.bordertech.flux;

import com.github.bordertech.flux.key.ActionKey;

/**
 * Actions are the payloads that are delivered via the dispatcher.
 *
 * @author Jonathan Austin
 * @param <T> the action payload type
 * @since 1.0.0
 */
public interface Action<T> {

	/**
	 *
	 * @return the action type and qualifier key
	 */
	ActionKey getKey();

	/**
	 *
	 * @return the action payload
	 */
	T getData();

}
