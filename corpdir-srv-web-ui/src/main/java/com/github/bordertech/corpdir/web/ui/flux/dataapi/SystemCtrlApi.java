package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.v1.SystemCtrlService;
import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;

/**
 * SystemCtrl CRUD API.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface SystemCtrlApi extends CorpCrudDataApi<SystemCtrl, SystemCtrlService> {

	Long getCurrentVersion();
}
