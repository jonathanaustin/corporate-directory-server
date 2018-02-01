package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.dataapi.OrgUnitApi;

/**
 * Org Unit Store with backing API.
 *
 * @author jonathan
 */
public interface OrgUnitStore extends CorpCrudTreeStore<OrgUnit, OrgUnitApi> {
}
