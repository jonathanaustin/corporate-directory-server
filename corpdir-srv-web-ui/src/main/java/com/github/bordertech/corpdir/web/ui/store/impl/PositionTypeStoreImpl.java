package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionTypeApi;
import com.github.bordertech.corpdir.web.ui.store.PositionTypeStore;
import javax.inject.Inject;

/**
 * Position Type Store with backing API implementation.
 *
 * @author jonathan
 */
public class PositionTypeStoreImpl extends DefaultCorpStore<PositionType, PositionTypeApi> implements PositionTypeStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionTypeStoreImpl(final PositionTypeApi api) {
		super(CorpEntityType.POSITION_TYPE, api);
	}
}
