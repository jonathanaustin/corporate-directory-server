package com.github.bordertech.flux.crud.store;

import com.github.bordertech.flux.crud.dataapi.CrudApi;
import com.github.bordertech.flux.store.DataApiStore;

/**
 * CRUD store using data API.
 *
 * @param <S> the search type
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <D> the CRUD data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataApiCrudStore<S, K, T, D extends CrudApi<S, K, T>> extends DataApiStore<D>, CrudStore<S, K, T> {

}
