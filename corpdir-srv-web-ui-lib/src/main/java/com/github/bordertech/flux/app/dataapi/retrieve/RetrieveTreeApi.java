package com.github.bordertech.flux.app.dataapi.retrieve;

import com.github.bordertech.taskmanager.service.ServiceException;
import java.util.List;

/**
 *
 * @author jonathan
 * @param <K> the entity key type
 * @param <T> the item type
 * @param <S> the search criteria type
 */
public interface RetrieveTreeApi<K, T, S> extends RetrieveEntityApi<K, T, S> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item) throws ServiceException;

	List<T> getRootItems() throws ServiceException;

	String getItemLabel(final T item);

	String getItemId(final T item);

}
