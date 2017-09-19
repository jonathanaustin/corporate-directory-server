package com.github.bordertech.wcomponents.lib.app.view.combo.input;

import com.github.bordertech.wcomponents.AbstractWMultiSelectList;
import com.github.bordertech.wcomponents.lib.app.view.input.MultiSelectOptionsView;
import com.github.bordertech.wcomponents.lib.app.view.input.impl.MultiSelectPairOptionsView;
import java.util.List;

/**
 * Polling Multi Select Pair options view.
 *
 * @author jonathan
 */
public class PollingMultiSelectPairOptionsView<S, T> extends PollingInputOptionsView<S, T> implements MultiSelectOptionsView<T> {

	public PollingMultiSelectPairOptionsView(final String qualifier) {
		super(qualifier, new MultiSelectPairOptionsView<T>());
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
