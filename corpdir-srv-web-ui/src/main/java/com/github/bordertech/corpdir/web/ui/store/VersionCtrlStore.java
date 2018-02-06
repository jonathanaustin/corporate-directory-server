package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.dataapi.VersionCtrlApi;
import com.github.bordertech.corpdir.web.ui.flux.store.CorpCrudStore;

/**
 * VersionCtrl Store with backing API.
 *
 * @author jonathan
 */
public interface VersionCtrlStore extends CorpCrudStore<VersionCtrl, VersionCtrlApi> {
}
