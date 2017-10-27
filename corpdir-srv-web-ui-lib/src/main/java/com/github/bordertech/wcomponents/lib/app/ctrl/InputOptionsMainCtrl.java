package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.OptionsEventType;
import com.github.bordertech.wcomponents.lib.app.view.input.InputOptionsView;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageEventType;
import java.util.List;

/**
 * Controller for a Input Options View.
 *
 * @author jonathan
 * @param <T> the options type
 */
public class InputOptionsMainCtrl<T> extends DefaultController {

	@Override
	public void setupController() {
		super.setupController();

		// OPTIONS Listeners
		for (OptionsEventType type : OptionsEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleOptionsEvent(event);
				}
			};
			registerListener(type, listener);
		}

	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getOptionsView() == null) {
			throw new IllegalStateException("An options view has not been set.");
		}
	}

	public final InputOptionsView<T> getOptionsView() {
		return getComponentModel().optionsView;
	}

	public final void setOptionsView(final InputOptionsView<T> optionsView) {
		getOrCreateComponentModel().optionsView = optionsView;
		addView(optionsView);
	}

	protected void handleOptionsEvent(final Event event) {
		OptionsEventType type = (OptionsEventType) event.getQualifier().getEventType();
		switch (type) {
			case RESET_OPTIONS:
				handleResetOptions();
				break;
			case LOAD_OPTIONS:
				List<T> options = (List<T>) event.getData();
				handleLoadOptions(options);
				break;
		}

	}

	protected void handleResetOptions() {
		getOptionsView().resetView();
	}

	protected void handleLoadOptions(final List<T> options) {
		if (options == null || options.isEmpty()) {
			dispatchMessage(MessageEventType.INFO, "No options found");
		} else {
			InputOptionsView<T> optView = getOptionsView();
			optView.setOptions(options);
			optView.setContentVisible(true);
		}
	}

	@Override
	protected OptionsMainModel<T> newComponentModel() {
		return new OptionsMainModel();
	}

	@Override
	protected OptionsMainModel<T> getComponentModel() {
		return (OptionsMainModel) super.getComponentModel();
	}

	@Override
	protected OptionsMainModel<T> getOrCreateComponentModel() {
		return (OptionsMainModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the view.
	 */
	public static class OptionsMainModel<T> extends CtrlModel {

		private InputOptionsView<T> optionsView;
	}

}
