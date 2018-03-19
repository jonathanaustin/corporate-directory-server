package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.DefaultCorpCrudActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.PositionTypeApi;
import javax.inject.Inject;

/**
 * Position Type action creator implementation.
 *
 * @author jonathan
 */
public class PositionTypeActionCreator extends DefaultCorpCrudActionCreator<PositionType, PositionTypeApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionTypeActionCreator(final PositionTypeApi api) {
		super(CorpEntityType.POSITION_TYPE, api);
	}
}
