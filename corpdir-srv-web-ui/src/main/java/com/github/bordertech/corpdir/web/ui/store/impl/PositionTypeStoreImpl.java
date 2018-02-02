package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudStore;
import com.github.bordertech.corpdir.web.ui.store.PositionTypeStore;
import javax.inject.Inject;

/**
 * PositionType Store with backing API implementation.
 *
 * @author jonathan
 */
public class PositionTypeStoreImpl extends DefaultCorpCrudStore<PositionType, PositionTypeApi> implements PositionTypeStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionTypeStoreImpl(final PositionTypeApi api) {
		super(CorpEntityType.POSITION_TYPE, api);
	}
}
