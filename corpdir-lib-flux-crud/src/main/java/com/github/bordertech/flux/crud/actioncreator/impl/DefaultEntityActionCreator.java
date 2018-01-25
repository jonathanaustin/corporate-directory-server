package com.github.bordertech.flux.crud.actioncreator.impl;

import com.github.bordertech.flux.crud.action.base.EntityActionBaseType;
import com.github.bordertech.flux.crud.actioncreator.EntityActionCreator;
import com.github.bordertech.flux.crud.dataapi.CrudApi;

/**
 * Modify action creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <D> the data API type
 */
public class DefaultEntityActionCreator<T, D extends CrudApi<T>> extends AbstractModifyActionCreator implements EntityActionCreator<T> {

	private final D api;

	public DefaultEntityActionCreator(final String key, final D api) {
		super(key);
		this.api = api;
	}

	@Override
	public T create(final T entity) {
		T created = getDataApi().create(entity);
		dispatchModifyAction(EntityActionBaseType.CREATE, created);
		return created;
	}

	@Override
	public T update(final T entity) {
		T updated = getDataApi().update(entity);
		dispatchModifyAction(EntityActionBaseType.UPDATE, updated);
		return updated;
	}

	@Override
	public void delete(final T entity) {
		getDataApi().delete(entity);
		dispatchModifyAction(EntityActionBaseType.DELETE, entity);
	}

	@Override
	public T createInstance() {
		return getDataApi().createInstance();
	}

	protected D getDataApi() {
		return api;
	}

}
