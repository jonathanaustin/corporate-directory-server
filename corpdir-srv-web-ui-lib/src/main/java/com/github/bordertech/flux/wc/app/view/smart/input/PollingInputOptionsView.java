package com.github.bordertech.flux.wc.app.view.smart.input;

import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.wc.app.view.InputOptionsView;
import com.github.bordertech.flux.wc.app.view.event.base.InputOptionsBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.smart.polling.DefaultPollingMessageSmartView;
import com.github.bordertech.flux.wc.view.DumbView;
import com.github.bordertech.wcomponents.AbstractWSelectList;
import java.util.List;

/**
 * Polling View and Input Options View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public class PollingInputOptionsView<S, T> extends DefaultPollingMessageSmartView<T> implements InputOptionsView<T> {
//public class PollingInputOptionsView<S, T> extends DefaultPollingMessageSmartView<T> implements InputOptionsView<T>, RetrieveListModelKey {

	private final InputOptionsView<T> optionsView;

	public PollingInputOptionsView(final String viewId, final InputOptionsView<T> optionsView) {
		super(viewId, "wclib/hbs/layout/combo-input-select.hbs");

		this.optionsView = optionsView;

		setBeanProperty(".");
		setSearchAncestors(true);
//		setBlocking(true);

		// Add views to holder
		addComponentToTemplate("vw-options", optionsView);

		// Default visibility
		getPollingView().setContentVisible(false);
		optionsView.setContentVisible(false);
	}

	public void startLoad(final S criteria) {
//		if (!isInitialised()) {
//			configViews();
//		}
//		dispatchEvent(PollingViewEvent.START_POLLING, criteria);
	}

	@Override
	public void configAjax(final DumbView view) {
		view.addEventAjaxTarget(this);
	}

	public InputOptionsView<T> getOptionsView() {
		return optionsView;
	}

	@Override
	public T getViewBean() {
		return optionsView.getViewBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		optionsView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		optionsView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return optionsView.validateView();
	}

//	@Override
//	public void setRetrieveListModelKey(final String key) {
////		ctrl.setRetrieveCollectionModelKey(key);
//	}
//
//	@Override
//	public String getRetrieveListModelKey() {
//		return "";
////		return ctrl.getRetrieveCollectionModelKey();
//	}
	@Override
	public void setOptions(final List<T> options) {
		optionsView.setOptions(options);
	}

	@Override
	public List<T> getOptions() {
		return optionsView.getOptions();
	}

	@Override
	public int getSize() {
		return optionsView.getSize();
	}

	@Override
	public AbstractWSelectList getSelectInput() {
		return optionsView.getSelectInput();
	}

	@Override
	public void showView(final boolean show) {
		optionsView.showView(show);
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		optionsView.doMakeFormReadonly(readonly);
	}

	@Override
	public void setCodeProperty(final String codeProperty) {
		optionsView.setCodeProperty(codeProperty);
	}

	@Override
	public String getCodeProperty() {
		return optionsView.getCodeProperty();
	}

	@Override
	public void setIncludeNullOption(final boolean includeNullOption) {
		optionsView.setIncludeNullOption(includeNullOption);
	}

	@Override
	public boolean isIncludeNullOption() {
		return optionsView.isIncludeNullOption();
	}

	protected void handleOptionsEvent(final Event event) {
		InputOptionsBaseViewEvent type = (InputOptionsBaseViewEvent) event.getEventKey().getEventType();
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
//			dispatchMessage(MessageEventType.INFO, "No options found");
		} else {
			InputOptionsView<T> optView = getOptionsView();
			optView.setOptions(options);
			optView.setContentVisible(true);
		}
	}

}
