package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.flux.wc.view.smart.CrudSearchSmartView;

/**
 * CRUD Smart View with CorpDir types.
 *
 * @param <T> the form entity type
 */
public interface CorpCrudSmartView<T extends ApiIdObject> extends CrudSearchSmartView<String, String, T> {

}
