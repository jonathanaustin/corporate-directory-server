package com.github.bordertech.flux;

import com.github.bordertech.flux.action.ActionKey;

/**
 * Actions are the payloads that are delivered via the dispatcher.
 *
 * @param <T> the action payload type
 *
 * @author Jonathan Austin
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
