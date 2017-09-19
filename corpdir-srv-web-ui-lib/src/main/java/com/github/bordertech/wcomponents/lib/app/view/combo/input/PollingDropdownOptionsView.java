package com.github.bordertech.wcomponents.lib.app.view.combo.input;

import com.github.bordertech.wcomponents.AbstractWSingleSelectList;
import com.github.bordertech.wcomponents.lib.app.view.input.SingleSelectOptionsView;
import com.github.bordertech.wcomponents.lib.app.view.input.impl.DropdownOptionsView;

/**
 * Polling dropdown options view.
 *
 * @author jonathan
 */
public class PollingDropdownOptionsView<S, T> extends PollingInputOptionsView<S, T> implements SingleSelectOptionsView<T> {

	public PollingDropdownOptionsView(final String qualifier) {
		super(qualifier, new DropdownOptionsView<T>());
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
