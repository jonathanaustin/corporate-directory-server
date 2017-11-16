package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.app.dataapi.modify.ModifyTreeApi;
import com.github.bordertech.flux.app.event.base.ModifyTreeBaseEventType;
import com.github.bordertech.taskmanager.service.ServiceException;

/**
 * Modify tree event creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <M> the modify API type
 */
public class DefaultModifyTreeCreator<T, M extends ModifyTreeApi<T>> extends DefaultModifyEntityCreator<T, M> implements ModifyTreeCreator<T> {

	public DefaultModifyTreeCreator(final M api) {
		super(api);
	}

	public DefaultModifyTreeCreator(final M api, final String qualifier) {
		super(api, qualifier);
	}

	@Override
	public T addChild(final T parent, final T child) throws ServiceException {
		T updated = getModifyApi().addChild(parent, child);
		dispatchModifyEvent(ModifyTreeBaseEventType.ADD_CHILD, updated);
		return updated;
	}

	@Override
	public T removeChild(final T parent, final T child) throws ServiceException {
		T updated = getModifyApi().removeChild(parent, child);
		dispatchModifyEvent(ModifyTreeBaseEventType.REMOVE_CHILD, updated);
		return updated;
	}

}
