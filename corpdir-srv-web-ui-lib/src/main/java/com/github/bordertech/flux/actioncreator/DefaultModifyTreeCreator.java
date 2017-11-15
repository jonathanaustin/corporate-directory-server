package com.github.bordertech.flux.actioncreator;

import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.dataapi.modify.ModifyTreeApi;
import com.github.bordertech.flux.event.base.ModifyTreeBaseEventType;
import com.github.bordertech.wcomponents.task.service.ServiceException;

/**
 * Modify tree event creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <M> the modify API type
 */
public class DefaultModifyTreeCreator<T, M extends ModifyTreeApi<T>> extends DefaultModifyCreator<T, M> implements ModifyTreeApi<T> {

	public DefaultModifyTreeCreator(final DataApiType type) {
		super(type);
	}

	public DefaultModifyTreeCreator(final DataApiType type, final String qualifier) {
		super(type, qualifier);
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
