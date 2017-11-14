package com.github.bordertech.flux.actioncreator;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.dataapi.DataApiException;
import com.github.bordertech.flux.dataapi.DataApiFactory;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.dataapi.modify.ModifyApi;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;
import com.github.bordertech.flux.event.base.ModifyEventType;
import com.github.bordertech.wcomponents.task.service.ResultHolder;

/**
 * Modify action creator used by views.
 *
 * @author jonathan
 */
public abstract class AbstractModifyActionCreator<S, T> implements ModifyApi<T> {

	private final ModifyApi<T> api;

	private final String qualifier;

	public AbstractModifyActionCreator(final DataApiType type) {
		this(type, null);
	}

	public AbstractModifyActionCreator(final DataApiType type, final String qualifier) {
		this.api = (ModifyApi<T>) DataApiFactory.getInstance(type);
		this.qualifier = qualifier;
	}

	public String getQualifier() {
		return qualifier;
	}

	@Override
	public T create(final T entity) throws DataApiException {
		T created = api.create(entity);
		dispatchModifyEvent(ModifyEventType.CREATE, created);
		return created;
	}

	@Override
	public T update(final T entity) throws DataApiException {
		T updated = api.update(entity);
		dispatchModifyEvent(ModifyEventType.UPDATE, updated);
		return updated;
	}

	@Override
	public void delete(final T entity) throws DataApiException {
		api.delete(entity);
		dispatchModifyEvent(ModifyEventType.DELETE, entity);
	}

	@Override
	public T createInstance() {
		return api.createInstance();
	}

	protected abstract S getKey(final T entity);

	protected void dispatchModifyEvent(final ModifyEventType eventType, final T entity) {
		ResultHolder holder = new ResultHolder(getKey(entity), entity);
		DefaultEvent event = new DefaultEvent(new EventKey(eventType, getQualifier()), holder);
		getDispatcher().dispatch(event);
	}

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

}
