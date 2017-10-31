package com.github.bordertech.flux.wc;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.impl.DefaultDispatcher;
import com.github.bordertech.flux.impl.DispatcherModel;
import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.UIContextHolder;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
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
