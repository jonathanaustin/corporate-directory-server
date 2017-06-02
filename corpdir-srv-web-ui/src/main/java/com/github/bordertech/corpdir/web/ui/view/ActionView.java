package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.web.ui.common.ActionMode;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.AjaxTarget;
import java.util.List;

/**
 * Action View interface.
 *
 * @author jonathan
 * @param <T> the API object
 */
public interface ActionView<T extends ApiKeyIdObject> {

	void load(final String id);

	void preLoad(final T bean);

	T getApiBean();

	List<ActionMode> getAllowedModes();

	void addActionAjaxTarget(final AjaxTarget target);

	Action getOnBackAction();

	void setOnBackAction(final Action action);

	Action getOnDeleteAction();

	void setOnDeleteAction(final Action action);

	Action getOnSaveAction();

	void setOnSaveAction(final Action action);

	T doRetrieveServiceCall(final String id) throws ServiceException;

	T doSaveServiceCall(final T bean) throws ServiceException;

	void doDeleteServiceCall(final T bean) throws ServiceException;

}
