package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.CorpCrudActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.CorpCrudDataApi;
import com.github.bordertech.flux.crud.actioncreator.impl.DefaultDataApiCrudActionCreator;

/**
 * Corp CRUD Action Creator with defined types.
 *
 * @param <T> the Corp API object type
 * @param <D> the backing Corp API
 *
 * @author jonathan
 */
public class DefaultCorpCrudActionCreator<T extends ApiIdObject, D extends CorpCrudDataApi<T, ?>> extends DefaultDataApiCrudActionCreator<String, T, D> implements CorpCrudActionCreator<T, D> {

	/**
	 * @param type the entity type
	 * @param api the backing data API
	 */
	public DefaultCorpCrudActionCreator(final CorpEntityType type, D api) {
		super(type.getActionCreatorKey(), api);
	}
}
