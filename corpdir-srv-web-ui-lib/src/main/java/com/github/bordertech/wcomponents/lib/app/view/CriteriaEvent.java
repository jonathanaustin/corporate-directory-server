package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.view.ViewEvent;

/**
 * Criteria events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CriteriaEvent extends ViewEvent {

	CriteriaEvent SEARCH = new CriteriaEvent() {
	};
}