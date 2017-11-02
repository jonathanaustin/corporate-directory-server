package com.github.bordertech.wcomponents.lib.app.view.form;

import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.wcomponents.lib.util.FormUtil;

/**
 * Default updateable view that can be used as a child a of a from view.
 *
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class FormUpdateableView<T> extends DefaultDumbView<T> implements FormUpdateable {

	public FormUpdateableView(final String viewId) {
		super(viewId);
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		FormUtil.doMakeInputsReadonly(this, readonly);
	}

}
