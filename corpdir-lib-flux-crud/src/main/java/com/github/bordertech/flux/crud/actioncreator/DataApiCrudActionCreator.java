package com.github.bordertech.flux.crud.actioncreator;

import com.github.bordertech.flux.actioncreator.DataApiActionCreator;
import com.github.bordertech.flux.crud.dataapi.CrudApi;

/**
 * CRUD Action Creator using data API.
 *
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <D> the CRUD data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataApiCrudActionCreator<K, T, D extends CrudApi<?, K, T>> extends DataApiActionCreator<D>, CrudActionCreator<T> {

}
