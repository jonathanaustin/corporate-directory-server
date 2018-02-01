package com.github.bordertech.corpdir.web.ui.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.actioncreator.SystemCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.dataapi.SystemCtrlApi;
import javax.inject.Inject;

/**
 * System Ctrl action creator implementation.
 *
 * @author jonathan
 */
public class SystemCtrlActionCreatorImpl extends DefaultCorpCrudActionCreator<SystemCtrl, SystemCtrlApi> implements SystemCtrlActionCreator {

	/**
	 * @param api the backing API
	 */
	@Inject
	public SystemCtrlActionCreatorImpl(final SystemCtrlApi api) {
		super(CorpEntityType.SYSTEM_CTRL, api);
	}
}
