package com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.DefaultCorpCrudActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.SystemCtrlApi;
import javax.inject.Inject;

/**
 * System Ctrl action creator implementation.
 *
 * @author jonathan
 */
public class SystemCtrlActionCreator extends DefaultCorpCrudActionCreator<SystemCtrl, SystemCtrlApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public SystemCtrlActionCreator(final SystemCtrlApi api) {
		super(CorpEntityType.SYSTEM_CTRL, api);
	}
}
