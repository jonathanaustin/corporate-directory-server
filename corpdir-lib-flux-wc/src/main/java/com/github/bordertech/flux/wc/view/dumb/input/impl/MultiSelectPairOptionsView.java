package com.github.bordertech.flux.wc.view.dumb.input.impl;

import com.github.bordertech.wcomponents.AbstractWMultiSelectList;
import com.github.bordertech.wcomponents.WMultiSelectPair;

/**
 *
 * @param <T> the options type
 * @author jonathan
 */
public class MultiSelectPairOptionsView<T> extends AbstractMultiSelectOptionsView<T> {

	private final WMultiSelectPair multi = new WMultiSelectPair();

	public MultiSelectPairOptionsView(final String viewId) {
		super(viewId);
		getInputContainer().add(multi);
		multi.setBeanProperty(".");
		setupInputAjax();
	}

	@Override
	public AbstractWMultiSelectList getSelectInput() {
		return multi;
	}

}
