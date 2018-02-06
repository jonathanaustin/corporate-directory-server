package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.dataapi.OrgUnitApi;
import com.github.bordertech.corpdir.web.ui.flux.store.CorpCrudTreeVersionStore;

/**
 * OrgUnit Store with backing API.
 *
 * @author jonathan
 */
public interface OrgUnitStore extends CorpCrudTreeVersionStore<OrgUnit, OrgUnitApi> {
}
