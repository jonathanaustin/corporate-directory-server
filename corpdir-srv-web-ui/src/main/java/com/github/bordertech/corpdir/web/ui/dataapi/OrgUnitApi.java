package com.github.bordertech.corpdir.web.ui.dataapi;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.dataapi.CorpCrudTreeVersionApi;

/**
 * OrgUnit CRUD API.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitApi extends CorpCrudTreeVersionApi<OrgUnit, OrgUnitService> {
}
