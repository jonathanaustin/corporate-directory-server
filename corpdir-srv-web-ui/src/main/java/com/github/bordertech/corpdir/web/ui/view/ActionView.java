package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.web.ui.common.ActionMode;
import com.github.bordertech.corpdir.web.ui.common.RecordAction;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WComponent;

/**
 * Action View interface.
 *
 * @author jonathan
 * @param <T> the API object
 */
public interface ActionView<T extends ApiKeyIdObject> extends WComponent, MessageContainer {

	void load(final String id);

	void setApiBean(final T bean);

	T getApiBean();

	void addActionAjaxTarget(final AjaxTarget target);

	void setEventAction(final Action action, final RecordAction event);

	Action getEventAction(final RecordAction event);

	T doRetrieveServiceCall(final String id) throws ServiceException;

	T doSaveServiceCall(final T bean) throws ServiceException;

	void doDeleteServiceCall(final T bean) throws ServiceException;

	void handleAction(final RecordAction action);

	ActionMode getActionMode();

	boolean isLoaded();

	DetailView<T> getDetailView();

}
