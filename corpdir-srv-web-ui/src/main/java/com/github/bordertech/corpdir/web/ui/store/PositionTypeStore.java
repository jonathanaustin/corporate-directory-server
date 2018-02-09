package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.store.CorpCrudStore;

/**
 * PositionType Store with backing API.
 *
 * @author jonathan
 */
public interface PositionTypeStore extends CorpCrudStore<PositionType, PositionTypeApi> {
}
