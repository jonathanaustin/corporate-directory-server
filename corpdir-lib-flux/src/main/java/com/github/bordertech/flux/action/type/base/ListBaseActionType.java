package com.github.bordertech.flux.action.type.base;

import com.github.bordertech.flux.action.type.ModifyStoreActionType;

/**
 * List action type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ListBaseActionType implements ModifyStoreActionType {
	LOAD_ITEMS,
	RESET_ITEMS,
	ADD_ITEM,
	UPDATE_ITEM,
	REMOVE_ITEM
}
