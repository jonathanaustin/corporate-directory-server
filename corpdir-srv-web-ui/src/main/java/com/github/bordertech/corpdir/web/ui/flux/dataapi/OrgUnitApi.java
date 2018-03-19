package com.github.bordertech.corpdir.web.ui.flux.dataapi;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;

/**
 * OrgUnit CRUD API.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface OrgUnitApi extends CorpCrudTreeVersionDataApi<OrgUnit, OrgUnitService> {
}
