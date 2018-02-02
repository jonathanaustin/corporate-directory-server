package com.github.bordertech.flux.crud.actioncreator.impl;

import com.github.bordertech.flux.crud.action.base.CrudTreeActionBaseType;
import com.github.bordertech.flux.crud.actioncreator.DataApiCrudTreeActionCreator;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * CRUD Tree action creator with backing API.
 *
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <D> the data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDataApiCrudTreeActionCreator<K, T, D extends CrudTreeApi<?, K, T>> extends DefaultDataApiCrudActionCreator<K, T, D> implements DataApiCrudTreeActionCreator<K, T, D> {

	public DefaultDataApiCrudTreeActionCreator(final String key, final D api) {
		super(key, api);
	}

	@Override
	public T addChild(final T parent, final T child) {
		T updated = getDataApi().addChild(parent, child);
		dispatchModifyAction(CrudTreeActionBaseType.ADD_CHILD, new ImmutablePair<>(parent, child));
		return updated;
	}

	@Override
	public T removeChild(final T parent, final T child) {
		T updated = getDataApi().removeChild(parent, child);
		dispatchModifyAction(CrudTreeActionBaseType.REMOVE_CHILD, new ImmutablePair<>(parent, child));
		return updated;
	}

}
