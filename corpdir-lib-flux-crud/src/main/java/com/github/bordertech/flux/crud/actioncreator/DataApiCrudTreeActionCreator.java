package com.github.bordertech.flux.crud.actioncreator;

import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;

/**
 * CRUD Tree store using data API.
 *
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <D> the CRUD Tree data API
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataApiCrudTreeActionCreator<K, T, D extends CrudTreeApi<?, K, T>> extends DataApiCrudActionCreator<K, T, D>, CrudTreeActionCreator<T> {

}
