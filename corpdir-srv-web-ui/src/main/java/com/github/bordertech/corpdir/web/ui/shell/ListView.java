package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.Action;
import java.util.List;

/**
 * Entity form view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ListView<T> extends BasicView {

	void setEntities(final List<T> entities);

	List<T> getEntities();

	void setListMode(final ListMode mode);

	ListMode getListMode();

	void setSelectAction(final Action action);

	Action getSelectAction();

}
