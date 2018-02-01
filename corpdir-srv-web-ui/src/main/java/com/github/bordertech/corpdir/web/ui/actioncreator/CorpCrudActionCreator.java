package com.github.bordertech.corpdir.web.ui.actioncreator;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.dataapi.CorpCrudApi;
import com.github.bordertech.flux.crud.actioncreator.DataApiCrudActionCreator;

/**
 * Corp CRUD Action Creator.
 *
 * @param <T> the API type
 * @param <D> the backing data API
 * @author jonathan
 */
public interface CorpCrudActionCreator<T extends ApiIdObject, D extends CorpCrudApi<T, ?>> extends DataApiCrudActionCreator<String, T, D> {

}
