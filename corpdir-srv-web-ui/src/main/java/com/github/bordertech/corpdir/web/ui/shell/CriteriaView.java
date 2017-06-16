package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.Action;

/**
 * Entity Criteria View.
 *
 * @param <T> the criteria type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface CriteriaView<T> extends BasicView {

	T getCriteria();

	void setCriteriaAction(final Action action);

	Action getCriteriaAction();

}
