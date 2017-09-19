package com.github.bordertech.wcomponents.lib.app.view.input.impl;

import com.github.bordertech.wcomponents.AbstractWSingleSelectList;
import com.github.bordertech.wcomponents.WDropdown;

/**
 *
 * @param <T> the options type
 * @author jonathan
 */
public class DropdownOptionsView<T> extends AbstractSingleSelectOptionsView<T> {

	private final WDropdown dropdown = new WDropdown();

	public DropdownOptionsView() {
		getContent().add(dropdown);
		dropdown.setBeanProperty(".");
		setupInputAjax();
	}

	@Override
	public AbstractWSingleSelectList getSelectInput() {
		return dropdown;
	}

}
