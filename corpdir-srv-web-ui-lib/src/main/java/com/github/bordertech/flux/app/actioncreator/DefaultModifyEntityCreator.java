package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.app.dataapi.CrudApi;
import com.github.bordertech.flux.app.event.base.ModifyBaseEventType;

/**
 * Modify event creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <D> the data API type
 */
public class DefaultModifyEntityCreator<T, D extends CrudApi<T>> extends AbstractModifyCreator implements ModifyEntityCreator<T> {

	private final D api;

	public DefaultModifyEntityCreator(final D api) {
		this(api, null);
	}

	public DefaultModifyEntityCreator(final D api, final String qualifier) {
		super(qualifier);
		this.api = api;
	}

	@Override
	public T create(final T entity) {
		T created = getDataApi().create(entity);
		dispatchModifyEvent(ModifyBaseEventType.CREATE, created);
		return created;
	}

	@Override
	public T update(final T entity) {
		T updated = getDataApi().update(entity);
		dispatchModifyEvent(ModifyBaseEventType.UPDATE, updated);
		return updated;
	}

	@Override
	public void delete(final T entity) {
		getDataApi().delete(entity);
		dispatchModifyEvent(ModifyBaseEventType.DELETE, entity);
	}

	@Override
	public T createInstance() {
		return getDataApi().createInstance();
	}

	protected D getDataApi() {
		return api;
	}

}
