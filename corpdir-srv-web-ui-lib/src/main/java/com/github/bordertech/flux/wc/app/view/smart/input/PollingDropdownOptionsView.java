package com.github.bordertech.flux.wc.app.view.smart.input;

import com.github.bordertech.flux.wc.app.view.input.SingleSelectOptionsView;
import com.github.bordertech.flux.wc.app.view.input.impl.DropdownOptionsView;
import com.github.bordertech.wcomponents.AbstractWSingleSelectList;

/**
 * Polling dropdown options view.
 *
 * @author jonathan
 */
public class PollingDropdownOptionsView<S, T> extends PollingInputOptionsView<S, T> implements SingleSelectOptionsView<T> {

	public PollingDropdownOptionsView(final String viewId, final String qualifier) {
		super(viewId, new DropdownOptionsView<T>("vw-select"));
	}

	@Override
	public SingleSelectOptionsView<T> getOptionsView() {
		return (SingleSelectOptionsView) super.getOptionsView(); //To change body of generated methods, choose Tools | Templates.
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
