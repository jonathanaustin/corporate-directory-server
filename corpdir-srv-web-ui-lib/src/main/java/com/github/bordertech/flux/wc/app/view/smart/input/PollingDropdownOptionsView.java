package com.github.bordertech.flux.wc.app.view.smart.input;

import com.github.bordertech.flux.wc.app.view.dumb.input.SingleSelectOptionsView;
import com.github.bordertech.flux.wc.app.view.dumb.input.impl.DropdownOptionsView;
import com.github.bordertech.wcomponents.AbstractWSingleSelectList;

/**
 * Polling dropdown options view.
 *
 * @author jonathan
 */
public class PollingDropdownOptionsView<S, T> extends DefaultPollingInputOptionsView<S, T> implements SingleSelectOptionsView<T> {

	public PollingDropdownOptionsView(final String viewId, final String qualifier) {
		super(viewId, new DropdownOptionsView<T>("vw-select"));
	}

	@Override
	public SingleSelectOptionsView<T> getOptionsView() {
		return (SingleSelectOptionsView) super.getOptionsView();
	}

	@Override
	public AbstractWSingleSelectList getSelectInput() {
		return getOptionsView().getSelectInput();
	}

	@Override
	public T getSelectedOption() {
		return getOptionsView().getSelectedOption();
	}

	@Override
	public void setSelectedOption(final T option) {
		getOptionsView().setSelectedOption(option);
	}

	@Override
	public void clearSelectedOption() {
		getOptionsView().clearSelectedOption();
	}

}
