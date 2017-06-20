package com.github.bordertech.corpdir.web.ui.shell;

import java.util.List;

/**
 * Entity list view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ListView<T> extends BasicEventView {

	void addListAction(final ListEvent event, final ListAction<T> action);

	void setEntities(final List<T> entities);

	List<T> getEntities();

	void setListMode(final ListMode mode);

	ListMode getListMode();

	void setSelectedIdx(final int idx);

	int getSelectedIdx();

	int getSize();

	T getSelected();

}
