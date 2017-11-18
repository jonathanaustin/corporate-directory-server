package com.github.bordertech.flux.key;

/**
 * Key used to identify dispatched actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ActionKey extends AbstractKey<ActionType> {

	public ActionKey(final ActionType actionType) {
		this(actionType, null);
	}

	public ActionKey(final String qualifier) {
		this(null, qualifier);
	}

	public ActionKey(final ActionType actionType, final String qualifier) {
		super(actionType, qualifier);
	}

}
