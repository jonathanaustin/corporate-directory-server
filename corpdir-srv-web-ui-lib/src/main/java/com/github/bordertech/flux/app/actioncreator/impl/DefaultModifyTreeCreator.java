package com.github.bordertech.flux.app.actioncreator.impl;

import com.github.bordertech.flux.app.action.base.ModifyTreeBaseActionType;
import com.github.bordertech.flux.app.actioncreator.ModifyTreeCreator;
import com.github.bordertech.flux.app.dataapi.CrudTreeApi;

/**
 * Modify tree action creator used by views.
 *
 * @author jonathan
 * @param <T> the entity type
 * @param <D> the data API type
 */
public class DefaultModifyTreeCreator<T, D extends CrudTreeApi<T>> extends DefaultModifyEntityCreator<T, D> implements ModifyTreeCreator<T> {

	public DefaultModifyTreeCreator(final String key, final D api) {
		super(key, api);
	}

	@Override
	public T addChild(final T parent, final T child) {
		T updated = getDataApi().addChild(parent, child);
		dispatchModifyAction(ModifyTreeBaseActionType.ADD_CHILD, updated);
		return updated;
	}

	@Override
	public T removeChild(final T parent, final T child) {
		T updated = getDataApi().removeChild(parent, child);
		dispatchModifyAction(ModifyTreeBaseActionType.REMOVE_CHILD, updated);
		return updated;
	}

}
