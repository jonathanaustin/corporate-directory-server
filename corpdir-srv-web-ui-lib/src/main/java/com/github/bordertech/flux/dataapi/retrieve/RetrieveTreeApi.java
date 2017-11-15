package com.github.bordertech.flux.dataapi.retrieve;

import com.github.bordertech.wcomponents.task.service.ServiceException;
import java.util.List;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public interface RetrieveTreeApi<S, T> extends RetrieveListApi<S, T> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item) throws ServiceException;

	List<T> getRootItems() throws ServiceException;

	String getItemLabel(final T item);

	String getItemId(final T item);

}
