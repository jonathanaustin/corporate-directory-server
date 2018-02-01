package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.UnitTypeApi;
import com.github.bordertech.corpdir.web.ui.store.UnitTypeStore;
import javax.inject.Inject;

/**
 * Unit Type Store with backing API implementation.
 *
 * @author jonathan
 */
public class UnitTypeStoreImpl extends DefaultCorpStore<UnitType, UnitTypeApi> implements UnitTypeStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public UnitTypeStoreImpl(final UnitTypeApi api) {
		super(CorpEntityType.UNIT_TYPE, api);
	}
}
