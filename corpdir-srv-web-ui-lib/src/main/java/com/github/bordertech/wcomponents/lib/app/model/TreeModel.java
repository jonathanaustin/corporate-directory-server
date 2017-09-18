package com.github.bordertech.wcomponents.lib.app.model;

import java.util.List;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the data type
 */
public interface TreeModel<S, T> extends RetrieveModel<S, T> {

	boolean hasChildren(final T entity);

	List<T> getChildren(final T entity);

	String getItemLabel(final T entity);

}
