package com.github.bordertech.flux.crud.store;

import com.github.bordertech.taskmanager.service.AsyncException;
import java.util.List;

/**
 * Store that holds tree values.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public interface EntityTreeStore<T> extends EntityStore<T> {

	boolean hasChildren(final T item) throws RetrieveActionException;

	List<T> getChildren(final T item) throws RetrieveActionException;

	boolean isChildrenDone(final T item) throws AsyncException;

	List<T> getRootItems() throws RetrieveActionException;

	boolean isRootItemsDone() throws AsyncException;

	String getItemLabel(final T item);

	String getItemId(final T item);

}
