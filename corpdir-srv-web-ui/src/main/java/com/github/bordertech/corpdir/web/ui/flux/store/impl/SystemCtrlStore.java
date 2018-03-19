package com.github.bordertech.corpdir.web.ui.flux.store.impl;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.SystemCtrlApi;
import com.github.bordertech.corpdir.web.ui.flux.store.DefaultCorpCrudStore;
import javax.inject.Inject;

/**
 * SystemCtrl Store with backing API implementation.
 *
 * @author jonathan
 */
public class SystemCtrlStore extends DefaultCorpCrudStore<SystemCtrl, SystemCtrlApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public SystemCtrlStore(final SystemCtrlApi api) {
		super(CorpEntityType.SYSTEM_CTRL, api);
	}
}
