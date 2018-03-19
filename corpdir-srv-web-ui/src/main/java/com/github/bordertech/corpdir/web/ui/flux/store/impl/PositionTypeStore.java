package com.github.bordertech.corpdir.web.ui.flux.store.impl;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.PositionTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.store.DefaultCorpCrudStore;
import javax.inject.Inject;

/**
 * PositionType Store with backing API implementation.
 *
 * @author jonathan
 */
public class PositionTypeStore extends DefaultCorpCrudStore<PositionType, PositionTypeApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionTypeStore(final PositionTypeApi api) {
		super(CorpEntityType.POSITION_TYPE, api);
	}
}
