package com.github.bordertech.flux.crud.action.base;

import com.github.bordertech.flux.crud.action.ModifyActionType;

/**
 * Modify entity action type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum EntityActionBaseType implements ModifyActionType {
	DELETE,
	CREATE,
	UPDATE
}
