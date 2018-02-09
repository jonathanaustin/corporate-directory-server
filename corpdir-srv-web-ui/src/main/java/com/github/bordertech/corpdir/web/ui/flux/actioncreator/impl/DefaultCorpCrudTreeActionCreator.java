package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.CorpCrudTreeActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.CorpCrudTreeDataApi;
import com.github.bordertech.flux.crud.actioncreator.impl.DefaultDataApiCrudTreeActionCreator;

/**
 * Corp CRUD Tree Action Creator with defined types.
 *
 * @param <T> the Corp API object type
 * @param <D> the backing Corp API
 *
 * @author jonathan
 */
public class DefaultCorpCrudTreeActionCreator<T extends ApiTreeable, D extends CorpCrudTreeDataApi<T, ?>> extends DefaultDataApiCrudTreeActionCreator<String, T, D> implements CorpCrudTreeActionCreator<T, D> {

	/**
	 * @param type the entity type
	 * @param api the backing data API
	 */
	public DefaultCorpCrudTreeActionCreator(final CorpEntityType type, D api) {
		super(type.getActionCreatorKey(), api);
	}
}
