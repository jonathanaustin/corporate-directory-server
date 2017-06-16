package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.Action;

/**
 * Entity Criteria View.
 *
 * @param <T> the criteria type
 * @param <R> the result type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingView<T, R> extends BasicView {

	void setCriteria(final T criteria);

	T getCriteria();

	R getResult();

	void refreshView();

	void setLoadedAction(final Action action);

	Action getLoadedAction();

}
