package com.github.bordertech.flux.crud.action.base;

import com.github.bordertech.flux.action.type.ModifyStoreActionType;

/**
 * Modify entity action type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum CrudActionBaseType implements ModifyStoreActionType {
	DELETE,
	CREATE,
	UPDATE
}
