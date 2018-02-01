package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.CorpCrudApi;
import com.github.bordertech.corpdir.web.ui.store.CorpCrudStore;
import com.github.bordertech.flux.crud.store.impl.DefaultDataApiCrudStore;

/**
 * Default Corp Store.
 *
 * @param <T> the CorpDir API Object
 * @param <D> the CorpDir data API type
 * @author jonathan
 */
public class DefaultCorpStore<T extends ApiIdObject, D extends CorpCrudApi<T, ?>> extends DefaultDataApiCrudStore<String, String, T, D> implements CorpCrudStore<T, D> {

	/**
	 * @param type the corp entity type
	 * @param api the backing API
	 */
	public DefaultCorpStore(final CorpEntityType type, final D api) {
		super(type.getStoreKey(), CorpEntityType.getLinkedCreators(type), api);
	}

}
