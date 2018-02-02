package com.github.bordertech.flux.crud.dataapi;

import java.util.List;

/**
 * CRUD Entity with a Tree Structure API.
 *
 * @param <S> the search criteria
 * @param <K> the entity key type
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudTreeApi<S, K, T> extends CrudApi<S, K, T> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item);

	List<T> getRootItems();

	String getItemLabel(final T item);

	String getItemId(final T item);

	T addChild(final T parent, final T child);

	T removeChild(final T parent, final T child);
}
