package com.github.bordertech.flux.action;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.ActionType;

/**
 * Default action that holds the action key and payload.
 *
 * @author Jonathan Austin
 * @param <T> the action payload type
 * @since 1.0.0
 */
public class DefaultAction<T> implements Action<T> {

	private final ActionKey key;
	private final T data;

	public DefaultAction(final ActionType actionType) {
		this(actionType, null);
	}

	public DefaultAction(final ActionType actionType, final T data) {
		this(actionType, null, data);
	}

	public DefaultAction(final ActionType actionType, final String qualifier, final T data) {
		this(new ActionKey(actionType, qualifier), data);
	}

	public DefaultAction(final ActionKey key, final T data) {
		if (key == null) {
			throw new IllegalArgumentException("An action key must be provided.");
		}
		if (key.getType() == null) {
			throw new IllegalArgumentException("An action type must be provided.");
		}
		this.key = key;
		this.data = data;
	}

	@Override
	public ActionKey getKey() {
		return key;
	}

	@Override
	public T getData() {
		return data;
	}

}
