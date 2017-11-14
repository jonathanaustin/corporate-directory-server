package com.github.bordertech.flux.actioncreator;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.dataapi.DataApiException;
import com.github.bordertech.flux.dataapi.DataApiFactory;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.dataapi.action.ActionApi;
import com.github.bordertech.flux.dispatcher.DefaultEvent;
import com.github.bordertech.flux.dispatcher.DispatcherFactory;
import com.github.bordertech.flux.event.base.ActionEventType;
import com.github.bordertech.wcomponents.task.service.ResultHolder;

/**
 * Action creator used by views.
 *
 * @author jonathan
 */
public abstract class AbstractActionCreator<S, T> implements ActionApi<T> {

	private final ActionApi<T> api;

	private final String qualifier;

	public AbstractActionCreator(final DataApiType type) {
		this(type, null);
	}

	public AbstractActionCreator(final DataApiType type, final String qualifier) {
		this.api = (ActionApi<T>) DataApiFactory.getInstance(type);
		this.qualifier = qualifier;
	}

	public String getQualifier() {
		return qualifier;
	}

	@Override
	public T create(final T entity) throws DataApiException {
		T created = api.create(entity);
		dispatchActionEvent(ActionEventType.CREATE, created);
		return created;
	}

	@Override
	public T update(final T entity) throws DataApiException {
		T updated = api.update(entity);
		dispatchActionEvent(ActionEventType.UPDATE, updated);
		return updated;
	}

	@Override
	public void delete(final T entity) throws DataApiException {
		api.delete(entity);
		dispatchActionEvent(ActionEventType.DELETE, entity);
	}

	@Override
	public T createInstance() {
		return api.createInstance();
	}

	protected abstract S getKey(final T entity);

	protected void dispatchActionEvent(final ActionEventType eventType, final T entity) {
		ResultHolder holder = new ResultHolder(getKey(entity), entity);
		DefaultEvent event = new DefaultEvent(new EventKey(eventType, getQualifier()), holder);
		getDispatcher().dispatch(event);
	}

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

}
