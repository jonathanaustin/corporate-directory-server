package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.OrgUnitApi;
import com.github.bordertech.corpdir.web.ui.store.OrgUnitStore;
import javax.inject.Inject;

/**
 * Org Unit Store with backing API implementation.
 *
 * @author jonathan
 */
public class OrgUnitStoreImpl extends DefaultCorpTreeStore<OrgUnit, OrgUnitApi> implements OrgUnitStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public OrgUnitStoreImpl(final OrgUnitApi api) {
		super(CorpEntityType.ORG_UNIT, api);
	}
}
