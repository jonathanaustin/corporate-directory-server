package com.github.bordertech.corpdir.web.ui.actioncreator;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.web.ui.dataapi.CorpCrudTreeApi;
import com.github.bordertech.flux.crud.actioncreator.DataApiCrudTreeActionCreator;

/**
 * Corp CRUD Tree Action Creator.
 *
 * @param <T> the API type
 * @param <D> the backing data API
 * @author jonathan
 */
public interface CorpCrudTreeActionCreator<T extends ApiTreeable, D extends CorpCrudTreeApi<T, ?>> extends CorpCrudActionCreator<T, D>, DataApiCrudTreeActionCreator<String, T, D> {

}
