package com.github.bordertech.flux.crud.actioncreator.impl;

import com.github.bordertech.flux.actioncreator.impl.DefaultActionCreator;
import com.github.bordertech.flux.crud.action.base.CrudActionBaseType;
import com.github.bordertech.flux.crud.actioncreator.DataApiCrudActionCreator;
import com.github.bordertech.flux.crud.dataapi.CrudApi;

/**
 * Modify action creator used by views.
 *
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <D> the data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDataApiCrudActionCreator<K, T, D extends CrudApi<?, K, T>> extends DefaultActionCreator implements DataApiCrudActionCreator<K, T, D> {

	private final D api;

	public DefaultDataApiCrudActionCreator(final String key, final D api) {
		super(key);
		this.api = api;
	}

	@Override
	public T create(final T entity) {
		T created = getDataApi().create(entity);
		dispatchModifyAction(CrudActionBaseType.CREATE, created);
		return created;
	}

	@Override
	public T update(final T entity) {
		T updated = getDataApi().update(entity);
		dispatchModifyAction(CrudActionBaseType.UPDATE, updated);
		return updated;
	}

	@Override
	public void delete(final T entity) {
		getDataApi().delete(entity);
		dispatchModifyAction(CrudActionBaseType.DELETE, entity);
	}

	@Override
	public T createInstance() {
		return getDataApi().createInstance();
	}

	@Override
	public D getDataApi() {
		return api;
	}

}
