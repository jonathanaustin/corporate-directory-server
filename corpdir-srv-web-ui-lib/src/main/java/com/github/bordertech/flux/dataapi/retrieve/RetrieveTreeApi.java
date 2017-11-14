package com.github.bordertech.flux.dataapi.retrieve;

import com.github.bordertech.flux.dataapi.DataApiException;
import java.util.List;

/**
 *
 * @author jonathan
 * @param <T> the item type
 */
public interface RetrieveTreeApi<S, T> extends RetrieveListApi<S, T> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item) throws DataApiException;

	String getItemLabel(final T item) throws DataApiException;

	String getItemId(final T item) throws DataApiException;
}
