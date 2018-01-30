package com.github.bordertech.flux.wc.view.smart.input;

import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.FluxDumbView;
import com.github.bordertech.flux.wc.view.dumb.InputOptionsView;
import com.github.bordertech.flux.wc.view.smart.polling.AbstractPollingRetrieveSmartView;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.wcomponents.AbstractWSelectList;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.lib.polling.PollingStartType;
import java.util.List;

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
		super(viewId, TemplateConstants.TEMPLATE_INPUT_SELECT);

		this.optionsView = optionsView;

		setBeanProperty(".");
		setSearchAncestors(true);
		setAjaxContext(true);

		// Add views to holder
		addComponentToTemplate(TemplateConstants.TAG_VW_OPTIONS, optionsView);

		// Automatically load
		setStartType(PollingStartType.AUTOMATIC);

		// Default visibility
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

	@Override
	public void setUseReadonlyContainer(final boolean useReadonlyPanel) {
		optionsView.setUseReadonlyContainer(useReadonlyPanel);
	}

	@Override
	public boolean isUseReadonlyContainer() {
		return optionsView.isUseReadonlyContainer();
	}

	@Override
	public WDiv getReadonlyContainer() {
		return optionsView.getReadonlyContainer();
	}

	@Override
	public WDiv getInputContainer() {
		return optionsView.getInputContainer();
	}

	@Override
	protected ResultHolder<S, List<T>> handleRetrieveStoreResult() {
		// FIXME
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
