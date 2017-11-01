package com.github.bordertech.wcomponents.lib.app.view.input.impl;

import com.github.bordertech.wcomponents.lib.app.view.event.SelectViewEvent;
import com.github.bordertech.wcomponents.lib.app.view.input.MultiSelectOptionsView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T> the options type
 * @author jonathan
 */
public abstract class AbstractMultiSelectOptionsView<T> extends AbstractInputOptionsView<T> implements MultiSelectOptionsView<T> {

	@Override
	public Object getBeanValue() {
		Object beanValue = super.getBeanValue();
		if (isBoundByCode()) {
			return getOptionsForCodes((List<?>) beanValue);
		}
		return beanValue;
	}

	@Override
	public void updateBeanValue() {
		List<T> selected = getSelectedOptions();
		if (isBoundByCode()) {
			List<?> codes = getCodesForOptions(selected);
			doUpdateBeanValue(codes);
		} else {
			doUpdateBeanValue(selected);
		}
	}

	@Override
	public List<T> getSelectedOptions() {
		return (List<T>) getSelectInput().getSelected();
	}

	@Override
	public void setSelectedOptions(final List<T> options) {
		getSelectInput().setSelected(options);
	}

	@Override
	public void addSelectedOption(final T option) {
		List<T> selected = getModifiableSelected();
		selected.add(option);
		setSelectedOptions(selected);
	}

	@Override
	public void removeSelectedOption(final T option) {
		List<T> selected = getModifiableSelected();
		selected.remove(option);
		setSelectedOptions(selected);
	}

	@Override
	public void clearSelectedOptions() {
		getSelectInput().resetData();
	}

	@Override
	protected void doDispatchSelectEvent() {
		List<T> beans = getSelectedOptions();
		if (beans == null) {
			dispatchViewEvent(SelectViewEvent.UNSELECT);
		} else {
			dispatchViewEvent(SelectViewEvent.SELECT, beans);
		}
	}

	protected List<T> getOptionsForCodes(final List<?> codes) {
		List<T> options = getOptions();
		if (options == null) {
			return null;
		}
		String codeProperty = getCodeProperty();
		List<T> opts = new ArrayList<>();
		for (Object code : codes) {
			T option = getOptionForCode(code, options, codeProperty);
			opts.add(option);
		}
		return opts;
	}

	protected List<?> getCodesForOptions(final List<T> options) {
		if (options == null) {
			return null;
		}
		String codeProperty = getCodeProperty();
		List<Object> codes = new ArrayList<>();
		for (T option : options) {
			Object code = getCodeForOption(option, codeProperty);
			codes.add(code);
		}
		return codes;
	}

	protected List<T> getModifiableSelected() {
		List<T> selected = new ArrayList<>();
		List<T> current = (List<T>) getSelectInput().getSelected();
		if (current != null) {
			selected.addAll(current);
		}
		return selected;
	}

}
