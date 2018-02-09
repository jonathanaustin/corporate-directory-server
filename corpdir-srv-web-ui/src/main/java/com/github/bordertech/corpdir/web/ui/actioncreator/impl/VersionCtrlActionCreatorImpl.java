package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.VersionCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.VersionCtrlApi;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.DefaultCorpCrudActionCreator;
import javax.inject.Inject;

/**
 * Version Ctrl action creator implementation.
 *
 * @author jonathan
 */
public class VersionCtrlActionCreatorImpl extends DefaultCorpCrudActionCreator<VersionCtrl, VersionCtrlApi> implements VersionCtrlActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public VersionCtrlActionCreatorImpl(final VersionCtrlApi api) {
		super(CorpEntityType.VERSION_CTRL, api);
	}
}
