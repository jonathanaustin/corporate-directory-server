package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.mvc.ViewBound;
import java.util.List;

/**
 * Entity list view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ListView<T> extends ViewBound<List<T>> {

	void addItem(final T entity);

	void removeItem(final T entity);

	void updateItem(final T entity);

	void showList(final boolean show);

}
