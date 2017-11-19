package com.github.bordertech.flux.app.action.base;

import com.github.bordertech.flux.app.action.RetrieveActionType;

/**
 * Retrieve actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum RetrieveBaseActionType implements RetrieveActionType {
	FETCH,
	SEARCH,
	CHILDREN,
	ROOT
}
