package com.github.bordertech.flux.crud.actioncreator.impl;

import com.github.bordertech.flux.crud.action.base.EntityTreeActionType;
import com.github.bordertech.flux.crud.actioncreator.EntityTreeActionCreator;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;

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
