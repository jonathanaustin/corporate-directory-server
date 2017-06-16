package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.Action;

/**
 * Entity Criteria View.
 *
 * @param <V> the criteria type
 * @param <T> the result type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingServiceView<V, T> extends BasicView {

	void setCriteria(final V criteria);

	V getCriteria();

	T getResult();

	void refreshView();

	void setLoadedAction(final Action action);

}
