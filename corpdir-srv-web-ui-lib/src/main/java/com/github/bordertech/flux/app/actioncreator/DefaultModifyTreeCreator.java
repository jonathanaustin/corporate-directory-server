package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.app.dataapi.CrudTreeApi;
import com.github.bordertech.flux.app.event.base.ModifyTreeBaseEventType;

/**
 * Modify tree event creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <D> the data API type
 */
public class DefaultModifyTreeCreator<T, D extends CrudTreeApi<T>> extends DefaultModifyEntityCreator<T, D> implements ModifyTreeCreator<T> {

	public DefaultModifyTreeCreator(final D api) {
		super(api);
	}

	public DefaultModifyTreeCreator(final D api, final String qualifier) {
		super(api, qualifier);
	}

	@Override
	public T addChild(final T parent, final T child) {
		T updated = getDataApi().addChild(parent, child);
		dispatchModifyEvent(ModifyTreeBaseEventType.ADD_CHILD, updated);
		return updated;
	}

	@Override
	public T removeChild(final T parent, final T child) {
		T updated = getDataApi().removeChild(parent, child);
		dispatchModifyEvent(ModifyTreeBaseEventType.REMOVE_CHILD, updated);
		return updated;
	}

}
