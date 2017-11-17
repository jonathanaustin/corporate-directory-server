package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.List;

/**
 * Store that holds tree values.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 * @param <K> the entity key type
 */
public interface RetrieveTreeStore<S, T, K> extends RetrieveEntityStore<S, T, K> {

	boolean hasChildren(final T item);

	List<T> getChildren(final T item);

	ServiceStatus getChildrenStatus(final T item);

	boolean isChildrenDone(final T item);

	List<T> getRootItems();

	ServiceStatus getRootItemsStatus();

	boolean isRootItemsDone();

	String getItemLabel(final T item);

	String getItemId(final T item);

}
