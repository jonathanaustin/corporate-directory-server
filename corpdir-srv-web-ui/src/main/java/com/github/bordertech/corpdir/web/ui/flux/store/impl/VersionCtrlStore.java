package com.github.bordertech.corpdir.web.ui.flux.store.impl;

import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.VersionCtrlApi;
import com.github.bordertech.corpdir.web.ui.flux.store.DefaultCorpCrudStore;
import javax.inject.Inject;

/**
 * VersionCtrl Store with backing API implementation.
 *
 * @author jonathan
 */
public class VersionCtrlStore extends DefaultCorpCrudStore<VersionCtrl, VersionCtrlApi> {

	/**
	 * @param api the backing API
	 */
	@Inject
	public VersionCtrlStore(final VersionCtrlApi api) {
		super(CorpEntityType.VERSION_CTRL, api);
	}
}
