package com.github.bordertech.flux.crud.action.base;

import com.github.bordertech.flux.crud.action.RetrieveActionType;

/**
 * Retrieve actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum RetrieveActionBaseType implements RetrieveActionType {
	FETCH,
	SEARCH,
	CHILDREN,
	ROOT
}
