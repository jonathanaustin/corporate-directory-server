package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.OrgUnitActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.OrgUnitApi;
import javax.inject.Inject;

/**
 * Org Unit action creator implementation.
 *
 * @author jonathan
 */
public class OrgUnitActionCreatorImpl extends DefaultCorpCrudTreeActionCreator<OrgUnit, OrgUnitApi> implements OrgUnitActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public OrgUnitActionCreatorImpl(final OrgUnitApi api) {
		super(CorpEntityType.ORG_UNIT, api);
	}
}
