package com.github.bordertech.flux.crud.store;

import java.util.List;

/**
 * Store that holds tree values.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public interface EntityTreeStore<T> extends EntityStore<T> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item);

	boolean isChildrenDone(final T item);

	List<T> getRootItems();

	boolean isRootItemsDone();

	String getItemLabel(final T item);

	String getItemId(final T item);

}
