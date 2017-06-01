package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.web.ui.common.DetailViewMode;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.AjaxTarget;
import java.util.List;

/**
 * Detail View interface.
 *
 * @author jonathan
 * @param <T> the API object
 */
public interface DetailView<T extends ApiKeyIdObject> {

	void addActionAjaxTarget(final AjaxTarget target);

	String getApiId();

	void setApiId(final String id);

	List<DetailViewMode> getAllowedModes();

	void setAllowedModes(final List<DetailViewMode> allowedModes);

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
