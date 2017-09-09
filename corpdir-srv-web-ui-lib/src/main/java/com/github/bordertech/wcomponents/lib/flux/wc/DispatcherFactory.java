package com.github.bordertech.wcomponents.lib.flux.wc;

import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.UIContextHolder;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultDispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DispatcherModel;

/**
 *
 * @author jonathan
 */
public final class DispatcherFactory {

	private static final Dispatcher DISPATCHER = new DefaultDispatcher() {
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
	};

	private DispatcherFactory() {
	}

	public static Dispatcher getInstance() {
		if (UIContextHolder.getCurrent() == null) {
			throw new IllegalStateException("Cannot use the dispatcher without a user context");
		}
		return DISPATCHER;
	}

}
