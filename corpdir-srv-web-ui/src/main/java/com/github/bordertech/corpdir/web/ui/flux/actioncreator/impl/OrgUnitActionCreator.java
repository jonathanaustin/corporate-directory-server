package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.DefaultCorpCrudTreeActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.OrgUnitApi;
import javax.inject.Inject;

/**
 * Org Unit action creator implementation.
 *
 * @author jonathan
 */
public class OrgUnitActionCreator extends DefaultCorpCrudTreeActionCreator<OrgUnit, OrgUnitApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public OrgUnitActionCreator(final OrgUnitApi api) {
		super(CorpEntityType.ORG_UNIT, api);
	}
}
