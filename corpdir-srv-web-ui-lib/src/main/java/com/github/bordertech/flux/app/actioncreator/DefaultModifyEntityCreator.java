package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.app.action.base.ModifyBaseActionType;
import com.github.bordertech.flux.app.dataapi.CrudApi;
import com.github.bordertech.flux.key.CreatorKey;

/**
 * Modify action creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <D> the data API type
 */
public class DefaultModifyEntityCreator<T, D extends CrudApi<T>> extends AbstractModifyCreator implements ModifyEntityCreator<T> {

	private final D api;

	public DefaultModifyEntityCreator(final CreatorKey key, final D api) {
		super(key);
		this.api = api;
	}

	@Override
	public T create(final T entity) {
		T created = getDataApi().create(entity);
		dispatchModifyAction(ModifyBaseActionType.CREATE, created);
		return created;
	}

	@Override
	public T update(final T entity) {
		T updated = getDataApi().update(entity);
		dispatchModifyAction(ModifyBaseActionType.UPDATE, updated);
		return updated;
	}

	@Override
	public void delete(final T entity) {
		getDataApi().delete(entity);
		dispatchModifyAction(ModifyBaseActionType.DELETE, entity);
	}

	@Override
	public T createInstance() {
		return getDataApi().createInstance();
	}

	protected D getDataApi() {
		return api;
	}

}
