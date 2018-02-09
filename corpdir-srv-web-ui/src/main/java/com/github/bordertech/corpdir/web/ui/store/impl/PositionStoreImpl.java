package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionApi;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.DefaultCorpCrudTreeVersionStore;
import com.github.bordertech.corpdir.web.ui.store.PositionStore;
import javax.inject.Inject;

/**
 * Position Store with backing API implementation.
 *
 * @author jonathan
 */
public class PositionStoreImpl extends DefaultCorpCrudTreeVersionStore<Position, PositionApi> implements PositionStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionStoreImpl(final PositionApi api) {
		super(CorpEntityType.POSITION, api);
	}
}
