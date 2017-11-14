package com.github.bordertech.wcomponents.lib.app.model;

import java.util.List;

/**
 *
 * @author jonathan
 * @param <T> the item type
 */
public interface TreeModel<S, T> extends RetrieveListModel<S, T> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item);

	String getItemLabel(final T item);

	String getItemId(final T item);
}
