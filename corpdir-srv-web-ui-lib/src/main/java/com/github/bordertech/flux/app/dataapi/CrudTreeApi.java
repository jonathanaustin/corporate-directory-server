package com.github.bordertech.flux.app.dataapi;

import java.util.List;

/**
 * Entity with a Tree Structure crud API.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public interface CrudTreeApi<T> extends CrudApi<T> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item);

	List<T> getRootItems();

	String getItemLabel(final T item);

	String getItemId(final T item);

	T addChild(final T parent, final T child);

	T removeChild(final T parent, final T child);
}
