package com.github.bordertech.flux.wc.app.view.smart.input;

import com.github.bordertech.flux.wc.app.view.InputOptionsView;
import com.github.bordertech.flux.wc.app.view.smart.polling.AbstractPollingRetrieveSmartView;
import com.github.bordertech.wcomponents.AbstractWSelectList;
import java.util.List;
import com.github.bordertech.flux.wc.view.FluxDumbView;

/**
 * Polling View and Input Options View.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public class DefaultPollingInputOptionsView<S, T> extends AbstractPollingRetrieveSmartView<S, List<T>, T> implements InputOptionsView<T> {

	private final InputOptionsView<T> optionsView;

	public DefaultPollingInputOptionsView(final String viewId, final InputOptionsView<T> optionsView) {
		super(viewId, "wclib/hbs/layout/combo-input-select.hbs");

		this.optionsView = optionsView;

		setBeanProperty(".");
		setSearchAncestors(true);

		// Add views to holder
		addComponentToTemplate("vw-options", optionsView);

		// Default visibility
		getPollingView().setContentVisible(false);
		optionsView.setContentVisible(false);
	}

	@Override
	protected void handleRetrieveOKEvent(final List<T> options) {
		if (options == null || options.isEmpty()) {
//			dispatchMessage(MessageEventType.INFO, "No options found");
		} else {
			InputOptionsView<T> optView = getOptionsView();
			optView.setOptions(options);
			optView.setContentVisible(true);
		}
	}

	@Override
	public void configAjax(final FluxDumbView view) {
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

}
