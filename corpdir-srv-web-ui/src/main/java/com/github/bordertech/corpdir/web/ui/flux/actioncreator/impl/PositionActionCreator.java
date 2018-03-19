package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.DefaultCorpCrudTreeActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.PositionApi;
import javax.inject.Inject;

/**
 * Position action creator implementation.
 *
 * @author jonathan
 */
public class PositionActionCreator extends DefaultCorpCrudTreeActionCreator<Position, PositionApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public PositionActionCreator(final PositionApi api) {
		super(CorpEntityType.POSITION, api);
	}
}
