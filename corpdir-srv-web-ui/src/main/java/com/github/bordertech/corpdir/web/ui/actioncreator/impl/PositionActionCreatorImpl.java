package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.PositionActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionApi;
import javax.inject.Inject;

/**
 * Position action creator implementation.
 *
 * @author jonathan
 */
public class PositionActionCreatorImpl extends DefaultCorpCrudTreeActionCreator<Position, PositionApi> implements PositionActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionActionCreatorImpl(final PositionApi api) {
		super(CorpEntityType.POSITION, api);
	}
}
