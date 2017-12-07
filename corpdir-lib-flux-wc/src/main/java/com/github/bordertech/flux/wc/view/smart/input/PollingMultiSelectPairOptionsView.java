package com.github.bordertech.flux.wc.view.smart.input;

import com.github.bordertech.flux.wc.view.dumb.input.MultiSelectOptionsView;
import com.github.bordertech.flux.wc.view.dumb.input.impl.MultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.WMultiSelectPair;

/**
 * Polling Multi Select Pair options view.
 *
 * @author jonathan
 */
public class PollingMultiSelectPairOptionsView<S, T> extends DefaultPollingMultiSelectOptionsView<S, T> implements MultiSelectOptionsView<T> {

	public PollingMultiSelectPairOptionsView(final String viewId) {
		super(viewId, new MultiSelectPairOptionsView<T>("vw_select"));
	}

	@Override
	public MultiSelectPairOptionsView<T> getOptionsView() {
		return (MultiSelectPairOptionsView) super.getOptionsView();
	}

	@Override
	public WMultiSelectPair getSelectInput() {
		return (WMultiSelectPair) super.getSelectInput();
	}

}
