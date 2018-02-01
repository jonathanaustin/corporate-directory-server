package com.github.bordertech.flux.crud.store;

import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;

/**
 * CRUD Tree store using data API.
 *
 * @param <S> the search type
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <D> the CRUD Tree data API
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataApiCrudTreeStore<S, K, T, D extends CrudTreeApi<S, K, T>> extends DataApiCrudStore<S, K, T, D>, CrudTreeStore<S, K, T> {

}
