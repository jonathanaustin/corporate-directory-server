package com.github.bordertech.corpdir.web.ui.store.impl;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.dataapi.SystemCtrlApi;
import com.github.bordertech.corpdir.web.ui.store.SystemCtrlStore;
import javax.inject.Inject;

/**
 * System Ctrl Store with backing API implementation.
 *
 * @author jonathan
 */
public class SystemCtrlStoreImpl extends DefaultCorpStore<SystemCtrl, SystemCtrlApi> implements SystemCtrlStore {

	/**
	 * @param api the backing API
	 */
	@Inject
	public SystemCtrlStoreImpl(final SystemCtrlApi api) {
		super(CorpEntityType.SYSTEM_CTRL, api);
	}
}
