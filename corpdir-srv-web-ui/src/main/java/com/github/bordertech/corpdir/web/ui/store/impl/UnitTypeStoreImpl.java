package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.UnitTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.DefaultCorpCrudStore;
import com.github.bordertech.corpdir.web.ui.store.UnitTypeStore;
import javax.inject.Inject;

/**
 * UnitType Store with backing API implementation.
 *
 * @author jonathan
 */
public class UnitTypeStoreImpl extends DefaultCorpCrudStore<UnitType, UnitTypeApi> implements UnitTypeStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public UnitTypeStoreImpl(final UnitTypeApi api) {
		super(CorpEntityType.UNIT_TYPE, api);
	}
}
