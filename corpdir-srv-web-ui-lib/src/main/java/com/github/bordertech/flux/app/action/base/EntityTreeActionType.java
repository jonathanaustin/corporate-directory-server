package com.github.bordertech.flux.app.action.base;

import com.github.bordertech.flux.app.action.ModifyActionType;

/**
 * Modify tree entity action type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum EntityTreeActionType implements ModifyActionType {
	ADD_CHILD,
	REMOVE_CHILD
}
