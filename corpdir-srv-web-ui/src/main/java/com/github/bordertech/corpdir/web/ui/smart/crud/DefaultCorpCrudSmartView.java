package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.flux.wc.crud.smart.impl.DefaultCrudSmartView;
import com.github.bordertech.wcomponents.WComponent;

/**
 * Default CRUD Smart View with Corp Dir types.
 *
 * @param <T> the form entity type
 */
public class DefaultCorpCrudSmartView<T extends ApiIdObject> extends DefaultCrudSmartView<String, String, T> {

	public DefaultCorpCrudSmartView(final String viewId, final String title, final WComponent panel) {
		super(viewId, title, panel);
	}

}
