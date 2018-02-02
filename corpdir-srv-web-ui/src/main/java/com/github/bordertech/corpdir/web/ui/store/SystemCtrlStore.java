package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.dataapi.SystemCtrlApi;
import com.github.bordertech.corpdir.web.ui.flux.CorpCrudStore;

/**
 * SystemCtrl Store with backing API.
 *
 * @author jonathan
 */
public interface SystemCtrlStore extends CorpCrudStore<SystemCtrl, SystemCtrlApi> {
}
