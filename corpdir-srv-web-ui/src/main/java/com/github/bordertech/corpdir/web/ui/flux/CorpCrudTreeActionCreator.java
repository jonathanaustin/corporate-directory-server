package com.github.bordertech.corpdir.web.ui.flux;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.flux.crud.actioncreator.DataApiCrudTreeActionCreator;

/**
 * Corp CRUD Tree Action Creator with defined types.
 *
 * @param <T> the API type
 * @param <D> the backing data API
 * @author jonathan
 */
public interface CorpCrudTreeActionCreator<T extends ApiTreeable, D extends CorpCrudTreeDataApi<T, ?>> extends CorpCrudActionCreator<T, D>, DataApiCrudTreeActionCreator<String, T, D> {

}
