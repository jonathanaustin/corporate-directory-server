package com.github.bordertech.flux.crud.action.base;

import com.github.bordertech.flux.crud.action.RetrieveActionType;

/**
 * Retrieve async outcome.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum AsyncOutcomeBaseActionType implements RetrieveActionType {
	ASYNC_OK,
	ASYNC_ERROR
}
