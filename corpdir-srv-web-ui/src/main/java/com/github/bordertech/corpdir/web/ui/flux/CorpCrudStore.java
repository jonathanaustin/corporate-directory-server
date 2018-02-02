package com.github.bordertech.corpdir.web.ui.flux;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.flux.crud.store.DataApiCrudStore;

/**
 * Corp CRUD Store with backing data API.
 *
 * @author jonathan
 * @param <T> the CorpDir API Object
 * @param <D> the CorpDir data API type
 */
public interface CorpCrudStore<T extends ApiIdObject, D extends CorpCrudDataApi<T, ?>>
		extends DataApiCrudStore<String, String, T, D> {
}
