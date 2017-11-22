package com.github.bordertech.flux.action;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.ActionType;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultAction implements Action {

	private final ActionKey key;
	private final Object data;

	public DefaultAction(final ActionType actionType) {
		this(actionType, null);
	}

	public DefaultAction(final ActionType actionType, final Object data) {
		this(actionType, null, data);
	}

	public DefaultAction(final ActionType actionType, final String qualifier, final Object data) {
		this(new ActionKey(actionType, qualifier), data);
	}

	public DefaultAction(final ActionKey key, final Object data) {
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
	public Object getData() {
		return data;
	}

}
