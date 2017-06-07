package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WComponent;

/**
 * Apply actions to an Entity.
 *
 * @param <T> the entity type
 * @param <R> the entity id type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ActionView<T, R> extends WComponent, MessageContainer {

	void loadEntityById(final R id);

	void loadEntity(final T entity);

	T getEntity();

	R getEntityId();

	void createRecord();

	void handleEvent(final RecordEvent event);

	void addEventAjaxTarget(final AjaxTarget target);

	void setEventAction(final RecordEvent event, final Action action);

	Action getEventAction(final RecordEvent event);

	T doRetrieveServiceCall(final R id) throws ServiceException;

	T doSaveServiceCall(final T entity) throws ServiceException;

	void doDeleteServiceCall(final T entity) throws ServiceException;

	ActionMode getActionMode();

	boolean isLoaded();

	EntityView<T, R> getEntityView();

}
