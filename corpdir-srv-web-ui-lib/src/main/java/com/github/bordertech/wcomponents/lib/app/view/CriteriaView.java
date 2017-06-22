package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.view.BasicEventView;
import com.github.bordertech.wcomponents.lib.view.ViewAction;

/**
 * Entity Criteria View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface CriteriaView<T> extends BasicEventView {

	T getCriteria();

	void registerViewAction(final CriteriaEvent viewEvent, final ViewAction<CriteriaView<T>, CriteriaEvent> viewAction);

}
