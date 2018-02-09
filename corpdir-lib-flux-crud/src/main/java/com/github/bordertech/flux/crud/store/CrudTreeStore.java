package com.github.bordertech.flux.crud.store;

import com.github.bordertech.taskmaster.service.CallType;
import com.github.bordertech.taskmaster.service.ResultHolder;
import java.util.List;

/**
 * CRUD Tree Entity Store.
 *
 * @param <S> the search type
 * @param <K> the entity key type
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudTreeStore<S, K, T> extends CrudStore<S, K, T> {

	boolean hasChildren(final T item);

	ResultHolder<T, List<T>> getChildren(final T item, final CallType callType);

	ResultHolder<S, List<T>> getRootItems(final CallType callType);

	String getItemLabel(final T item);

	String getItemId(final T item);

}
