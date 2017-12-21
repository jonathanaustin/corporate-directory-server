package com.github.bordertech.flux.wc.view.smart.input;

import com.github.bordertech.flux.wc.view.dumb.input.SingleSelectOptionsView;
import com.github.bordertech.wcomponents.AbstractWSingleSelectList;

/**
 * Polling single select options view.
 *
 * @author jonathan
 */
public class DefaultPollingSingleSelectOptionsView<S, T> extends DefaultPollingInputOptionsView<S, T> implements SingleSelectOptionsView<T> {

	public DefaultPollingSingleSelectOptionsView(final String viewId, final SingleSelectOptionsView<T> optionsView) {
		super(viewId, optionsView);
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
