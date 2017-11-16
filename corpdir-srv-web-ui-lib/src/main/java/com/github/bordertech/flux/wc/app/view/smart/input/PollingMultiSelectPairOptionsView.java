package com.github.bordertech.flux.wc.app.view.smart.input;

import com.github.bordertech.flux.wc.app.view.input.MultiSelectOptionsView;
import com.github.bordertech.flux.wc.app.view.input.impl.MultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.AbstractWMultiSelectList;
import java.util.List;

/**
 * Polling Multi Select Pair options view.
 *
 * @author jonathan
 */
public class PollingMultiSelectPairOptionsView<S, T> extends DefaultPollingInputOptionsView<S, T> implements MultiSelectOptionsView<T> {

	public PollingMultiSelectPairOptionsView(final String viewId) {
		super(viewId, new MultiSelectPairOptionsView<T>("vw-select"));
	}

	@Override
	public MultiSelectOptionsView<T> getOptionsView() {
		return (MultiSelectOptionsView) super.getOptionsView();
	}

	@Override
	public AbstractWMultiSelectList getSelectInput() {
		return getOptionsView().getSelectInput();
	}

	@Override
	public List<T> getSelectedOptions() {
		return getOptionsView().getSelectedOptions();
	}

	@Override
	public void setSelectedOptions(final List<T> options) {
		getOptionsView().setSelectedOptions(options);
	}

	@Override
	public void addSelectedOption(final T option) {
		getOptionsView().addSelectedOption(option);
	}

	@Override
	public void removeSelectedOption(final T option) {
		getOptionsView().removeSelectedOption(option);
	}

	@Override
	public void clearSelectedOptions() {
		getOptionsView().clearSelectedOptions();
	}

}
