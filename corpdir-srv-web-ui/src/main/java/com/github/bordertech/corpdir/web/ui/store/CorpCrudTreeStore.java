package com.github.bordertech.corpdir.web.ui.store;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.web.ui.dataapi.*;
import com.github.bordertech.flux.crud.store.DataApiCrudTreeStore;

/**
 * Corp CRUD Tree Store with data API.
 *
 * @author jonathan
 * @param <T> the CorpDir API Object
 * @param <D> the CorpDir data API type
 */
public interface CorpCrudTreeStore<T extends ApiTreeable, D extends CorpCrudTreeApi<T, ?>>
		extends CorpCrudStore<T, D>, DataApiCrudTreeStore<String, String, T, D> {
}
