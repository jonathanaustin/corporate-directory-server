package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.flux.View;
import java.util.List;

/**
 * Entity list view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ListView<T> extends View {

	void setEntities(final List<T> entities);

	List<T> getEntities();

	void setListMode(final ListMode mode);

	ListMode getListMode();

	void clearSelectedIdx();

	void setSelectedIdx(final int idx);

	int getSelectedIdx();

	int getSize();

	T getSelected();

}
