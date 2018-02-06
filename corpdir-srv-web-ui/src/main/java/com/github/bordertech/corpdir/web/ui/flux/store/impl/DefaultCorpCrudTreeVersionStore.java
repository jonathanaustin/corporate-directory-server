package com.github.bordertech.corpdir.web.ui.flux.store.impl;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.SystemCtrlApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.CorpCrudTreeVersionDataApi;
import com.github.bordertech.corpdir.web.ui.flux.store.CorpCrudTreeVersionStore;
import com.github.bordertech.didums.Didums;
import com.github.bordertech.flux.crud.store.impl.DefaultDataApiCrudTreeStore;

/**
 * Default Corp Tree Store with backing API.
 *
 * @param <T> the CorpDir API Object
 * @param <D> the CorpDir data API type
 * @author jonathan
 */
public class DefaultCorpCrudTreeVersionStore<T extends ApiTreeable & ApiVersionable, D extends CorpCrudTreeVersionDataApi<T, ?>> extends DefaultDataApiCrudTreeStore<String, String, T, D> implements CorpCrudTreeVersionStore<T, D> {

	private static final SystemCtrlApi CTRL = Didums.getService(SystemCtrlApi.class);

	/**
	 * @param type the corp entity type
	 * @param api the backing API
	 */
	public DefaultCorpCrudTreeVersionStore(final CorpEntityType type, final D api) {
		super(type.getStoreKey(), CorpEntityType.getLinkedCreators(type), api);
	}

	@Override
	public Long getCurrentVersionId() {
		// TODO Should cache this and then make sure it ghets cleared when the version changes
		return CTRL.getCurrentVersion();
	}

	@Override
	protected String getCacheKey(final String action, final Object criteria) {
		// Append the version id
		return super.getCacheKey(action, criteria) + "-" + getCurrentVersionId();
	}

}
