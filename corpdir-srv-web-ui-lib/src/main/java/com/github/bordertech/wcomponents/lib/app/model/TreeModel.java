package com.github.bordertech.wcomponents.lib.app.model;

import java.util.List;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public interface TreeModel<S, T> extends SearchModel<S, T> {

	boolean hasChildren(final T entity);

	List<T> getChildren(final T entity);

	String getItemLabel(final T entity);

}
