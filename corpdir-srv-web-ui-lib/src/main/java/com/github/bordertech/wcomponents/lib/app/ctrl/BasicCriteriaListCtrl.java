package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.impl.BasicCriteriaView;
import com.github.bordertech.wcomponents.lib.app.impl.BasicListView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 *
 * @author jonathan
 */
public abstract class BasicCriteriaListCtrl<T> extends AbstractCriteriaListCtrl<String, T> {

	public BasicCriteriaListCtrl(final Dispatcher dispatcher) {
		super(new BasicCriteriaView(dispatcher), new BasicListView<T>(dispatcher));
	}

}
