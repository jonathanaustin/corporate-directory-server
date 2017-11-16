package com.github.bordertech.flux.app.event.base;

import com.github.bordertech.flux.app.event.RetrieveEventType;

/**
 * Model (ie entity) event type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum RetrieveBaseEventType implements RetrieveEventType {
	RETRIEVE,
	SEARCH,
	CHILDREN,
	ROOT
}
