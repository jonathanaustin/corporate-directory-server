package com.github.bordertech.corpdir.web.ui.flux.store;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.CorpCrudTreeDataApi;
import com.github.bordertech.flux.crud.store.impl.DefaultDataApiCrudTreeStore;

/**
 * Default Corp Tree Store with backing API.
 *
 * @param <T> the CorpDir API Object
 * @param <D> the CorpDir data API type
 * @author jonathan
 */
public class DefaultCorpCrudTreeStore<T extends ApiTreeable, D extends CorpCrudTreeDataApi<T, ?>> extends DefaultDataApiCrudTreeStore<String, String, T, D> implements CorpCrudTreeStore<T, D> {

	/**
	 * @param type the corp entity type
	 * @param api the backing API
	 */
	public DefaultCorpCrudTreeStore(final CorpEntityType type, final D api) {
		super(type.getStoreKey(), CorpEntityType.getLinkedCreators(type), api);
	}

}
