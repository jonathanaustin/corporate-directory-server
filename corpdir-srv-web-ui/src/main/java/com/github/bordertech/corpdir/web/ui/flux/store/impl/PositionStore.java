package com.github.bordertech.corpdir.web.ui.flux.store.impl;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.PositionApi;
import com.github.bordertech.corpdir.web.ui.flux.store.DefaultCorpCrudTreeVersionStore;
import javax.inject.Inject;

/**
 * Position Store with backing API implementation.
 *
 * @author jonathan
 */
public class PositionStore extends DefaultCorpCrudTreeVersionStore<Position, PositionApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionStore(final PositionApi api) {
		super(CorpEntityType.POSITION, api);
	}
}
