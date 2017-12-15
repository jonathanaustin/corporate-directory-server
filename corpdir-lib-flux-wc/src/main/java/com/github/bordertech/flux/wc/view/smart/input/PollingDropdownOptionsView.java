package com.github.bordertech.flux.wc.view.smart.input;

import com.github.bordertech.flux.wc.view.dumb.input.SingleSelectOptionsView;
import com.github.bordertech.flux.wc.view.dumb.input.impl.DropdownOptionsView;
import com.github.bordertech.wcomponents.WDropdown;

/**
 * Polling dropdown options view.
 *
 * @author jonathan
 */
public class PollingDropdownOptionsView<S, T> extends DefaultPollingSingleSelectOptionsView<S, T> implements SingleSelectOptionsView<T> {

	public PollingDropdownOptionsView(final String viewId) {
		super(viewId, new DropdownOptionsView<T>("vw_select"));
	}

	@Override
	public DropdownOptionsView<T> getOptionsView() {
		return (DropdownOptionsView<T>) super.getOptionsView();
	}

	@Override
	public WDropdown getSelectInput() {
		return (WDropdown) super.getSelectInput();
	}

}
