package com.github.bordertech.flux.wc.dispatcher;

import com.github.bordertech.flux.dispatcher.DispatcherModel;
import com.github.bordertech.flux.dispatcher.AbstractDispatcher;
import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.UIContextHolder;

/**
 * Dispatcher implementation using the WComponent UIC Context.
 *
 * @author jonathan
 */
public class DispatcherUicImpl extends AbstractDispatcher {

	@Override
	protected DispatcherModel getDispatcherModel() {
		UIContext uic = UIContextHolder.getCurrent();
		if (uic == null) {
			throw new IllegalStateException("Dont use the Dispatcher without a User Context");
		}
		DispatcherModel model = (DispatcherModel) uic.getFwkAttribute("wc-dis");
		if (model == null) {
			model = createNewModel();
			UIContextHolder.getCurrent().setFwkAttribute("wc-dis", model);
		}
		return model;
	}

}
