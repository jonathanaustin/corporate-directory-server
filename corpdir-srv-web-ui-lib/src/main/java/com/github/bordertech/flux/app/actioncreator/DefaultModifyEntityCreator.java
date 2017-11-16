package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.app.dataapi.modify.ModifyEntityApi;
import com.github.bordertech.flux.app.event.base.ModifyBaseEventType;
import com.github.bordertech.taskmanager.service.ServiceException;

/**
 * Modify event creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <M> the modify API type
 */
public class DefaultModifyEntityCreator<T, M extends ModifyEntityApi<T>> extends DefaultModifyCreator implements ModifyEntityCreator<T> {

	private final M api;

	public DefaultModifyEntityCreator(final M api) {
		this(api, null);
	}

	public DefaultModifyEntityCreator(final M api, final String qualifier) {
		super(qualifier);
		this.api = api;
	}

	@Override
	public T create(final T entity) throws ServiceException {
		T created = getModifyApi().create(entity);
		dispatchModifyEvent(ModifyBaseEventType.CREATE, created);
		return created;
	}

	@Override
	public T update(final T entity) throws ServiceException {
		T updated = getModifyApi().update(entity);
		dispatchModifyEvent(ModifyBaseEventType.UPDATE, updated);
		return updated;
	}

	@Override
	public void delete(final T entity) throws ServiceException {
		getModifyApi().delete(entity);
		dispatchModifyEvent(ModifyBaseEventType.DELETE, entity);
	}

	@Override
	public T createInstance() {
		return getModifyApi().createInstance();
	}

	protected M getModifyApi() {
		return api;
	}

}
