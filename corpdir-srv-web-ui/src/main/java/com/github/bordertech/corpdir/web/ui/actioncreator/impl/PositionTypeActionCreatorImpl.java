package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.PositionTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionTypeApi;
import javax.inject.Inject;

/**
 * Position Type action creator implementation.
 *
 * @author jonathan
 */
public class PositionTypeActionCreatorImpl extends DefaultCorpCrudActionCreator<PositionType, PositionTypeApi> implements PositionTypeActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionTypeActionCreatorImpl(final PositionTypeApi api) {
		super(CorpEntityType.POSITION_TYPE, api);
	}
}
