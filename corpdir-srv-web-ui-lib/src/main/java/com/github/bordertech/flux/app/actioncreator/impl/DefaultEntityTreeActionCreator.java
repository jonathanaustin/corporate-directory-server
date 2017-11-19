package com.github.bordertech.flux.app.actioncreator.impl;

import com.github.bordertech.flux.app.action.base.EntityTreeActionType;
import com.github.bordertech.flux.app.dataapi.CrudTreeApi;
import com.github.bordertech.flux.app.actioncreator.EntityTreeActionCreator;

/**
 * Modify tree action creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <D> the data API type
 */
public class DefaultEntityTreeActionCreator<T, D extends CrudTreeApi<T>> extends DefaultEntityActionCreator<T, D> implements EntityTreeActionCreator<T> {

	public DefaultEntityTreeActionCreator(final String key, final D api) {
		super(key, api);
	}

	@Override
	public T addChild(final T parent, final T child) {
		T updated = getDataApi().addChild(parent, child);
		dispatchModifyAction(EntityTreeActionType.ADD_CHILD, updated);
		return updated;
	}

	@Override
	public T removeChild(final T parent, final T child) {
		T updated = getDataApi().removeChild(parent, child);
		dispatchModifyAction(EntityTreeActionType.REMOVE_CHILD, updated);
		return updated;
	}

}
