package com.github.bordertech.flux.action.base;

import com.github.bordertech.flux.action.StoreActionType;

/**
 * List action type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ListBaseActionType implements StoreActionType {
	LOAD_ITEMS,
	RESET_ITEMS,
	ADD_ITEM,
	UPDATE_ITEM,
	REMOVE_ITEM
}
