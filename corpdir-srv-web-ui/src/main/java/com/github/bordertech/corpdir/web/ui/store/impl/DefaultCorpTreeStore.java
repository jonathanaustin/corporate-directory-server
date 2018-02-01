package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.CorpCrudTreeApi;
import com.github.bordertech.corpdir.web.ui.store.CorpCrudTreeStore;
import com.github.bordertech.flux.crud.store.impl.DefaultDataApiCrudTreeStore;

/**
 * Default Corp Tree Store.
 *
 * @param <T> the CorpDir API Object
 * @param <D> the CorpDir data API type
 * @author jonathan
 */
public class DefaultCorpTreeStore<T extends ApiTreeable, D extends CorpCrudTreeApi<T, ?>> extends DefaultDataApiCrudTreeStore<String, String, T, D> implements CorpCrudTreeStore<T, D> {

	/**
	 * @param type the corp entity type
	 * @param api the backing API
	 */
	public DefaultCorpTreeStore(final CorpEntityType type, final D api) {
		super(type.getStoreKey(), CorpEntityType.getLinkedCreators(type), api);
	}

}
