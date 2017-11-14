package com.github.bordertech.flux.dataapi.retrieve;

import java.util.List;

/**
 *
 * @author jonathan
 * @param <T> the item type
 */
public interface RetrieveTreeApi<S, T> extends RetrieveListApi<S, T> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item);

	String getItemLabel(final T item);

	String getItemId(final T item);
}
