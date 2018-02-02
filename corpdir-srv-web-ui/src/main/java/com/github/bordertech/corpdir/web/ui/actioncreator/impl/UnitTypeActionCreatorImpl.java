package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.UnitTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.UnitTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudActionCreator;
import javax.inject.Inject;

/**
 * Unit Type action creator implementation.
 *
 * @author jonathan
 */
public class UnitTypeActionCreatorImpl extends DefaultCorpCrudActionCreator<UnitType, UnitTypeApi> implements UnitTypeActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public UnitTypeActionCreatorImpl(final UnitTypeApi api) {
		super(CorpEntityType.UNIT_TYPE, api);
	}
}
