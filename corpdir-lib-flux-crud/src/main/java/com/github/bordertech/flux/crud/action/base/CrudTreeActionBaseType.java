package com.github.bordertech.flux.crud.action.base;

import com.github.bordertech.flux.action.type.ModifyStoreActionType;

/**
 * Modify tree entity action type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum CrudTreeActionBaseType implements ModifyStoreActionType {
	ADD_CHILD,
	REMOVE_CHILD
}
