package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.dataapi.UnitTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.store.CorpCrudStore;

/**
 * UnitType Store with backing API.
 *
 * @author jonathan
 */
public interface UnitTypeStore extends CorpCrudStore<UnitType, UnitTypeApi> {
}
