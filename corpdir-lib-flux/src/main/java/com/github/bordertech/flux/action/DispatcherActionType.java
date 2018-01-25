package com.github.bordertech.flux.action;

import com.github.bordertech.flux.key.ActionType;

/**
 * Dispatcher Actions.
 * <p>
 * The dispatcher uses these actions for its internal state by dispatching its own actions to itself. The dispatcher has
 * the matching listeners to respond to these actions.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum DispatcherActionType implements ActionType {
	REGISTER_LISTENER,
	UNREGISTER_LISTENER,
	REGISTER_STORE,
	UNREGISTER_STORE,
	REGISTER_CREATOR,
	UNREGISTER_CREATOR
}
