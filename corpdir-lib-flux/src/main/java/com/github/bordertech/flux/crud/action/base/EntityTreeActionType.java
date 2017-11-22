package com.github.bordertech.flux.crud.action.base;

import com.github.bordertech.flux.crud.action.ModifyActionType;

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
