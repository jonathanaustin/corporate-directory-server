package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.flux.wc.crud.smart.impl.DefaultCrudTreeSmartView;
import com.github.bordertech.wcomponents.WComponent;

/**
 * Default CRUD Smart View with Corp Dir types.
 *
 * @param <T> the form entity type
 */
public class DefaultCorpCrudTreeSmartView<T extends ApiTreeable> extends DefaultCrudTreeSmartView<String, String, T> {

	public DefaultCorpCrudTreeSmartView(final String viewId, final String title, final WComponent panel) {
		super(viewId, title, panel);
	}

}
