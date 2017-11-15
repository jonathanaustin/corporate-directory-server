package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.app.dataapi.modify.ModifyApi;
import com.github.bordertech.flux.app.event.ModifyEventType;
import com.github.bordertech.flux.app.event.base.ModifyBaseEventType;
import com.github.bordertech.flux.dataapi.DataApiFactory;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;
import com.github.bordertech.taskmanager.service.ServiceException;

/**
 * Modify event creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <M> the modify API type
 */
public class DefaultModifyCreator<T, M extends ModifyApi<T>> implements ModifyCreator<T> {

	private final M api;

	private final String qualifier;

	public DefaultModifyCreator(final DataApiType type) {
		this(type, null);
	}

	public DefaultModifyCreator(final DataApiType type, final String qualifier) {
		this.api = (M) DataApiFactory.getInstance(type);
		this.qualifier = qualifier;
	}

	public String getQualifier() {
		return qualifier;
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

	protected void dispatchModifyEvent(final ModifyEventType eventType, final T entity) {
		DefaultEvent event = new DefaultEvent(new EventKey(eventType, getQualifier()), entity);
		getDispatcher().dispatch(event);
	}

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

	protected M getModifyApi() {
		return api;
	}

}
