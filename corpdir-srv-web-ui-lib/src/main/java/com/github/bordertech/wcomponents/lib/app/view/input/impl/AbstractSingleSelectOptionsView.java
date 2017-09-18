package com.github.bordertech.wcomponents.lib.app.view.input.impl;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.event.SelectEventType;
import com.github.bordertech.wcomponents.lib.app.view.input.SingleSelectOptionsView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T> the options type
 * @author jonathan
 */
public abstract class AbstractSingleSelectOptionsView<T> extends DefaultView<T> implements SingleSelectOptionsView<T> {

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		setupInputAjax();
	}

	protected void setupInputAjax() {
		// Add AJAX COntrol for Select Input
		if (getSelectInput() instanceof AjaxTrigger) {
			AppAjaxControl ajax = new AppAjaxControl((AjaxTrigger) getSelectInput(), this);
			getContent().add(ajax);
			registerSelectUnselectAjaxControl(ajax);
		}
		// On Change Action
		getSelectInput().setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doDispatchSelectEvent();
			}
		});
	}

	protected void doDispatchSelectEvent() {
		T bean = getSelectedOption();
		if (bean == null) {
			dispatchEvent(SelectEventType.UNSELECT);
		} else {
			dispatchEvent(SelectEventType.SELECT, bean);
		}
	}

	protected void registerSelectUnselectAjaxControl(final AppAjaxControl ctrl) {
		registerEventAjaxControl(SelectEventType.UNSELECT, ctrl);
		registerEventAjaxControl(SelectEventType.SELECT, ctrl);
	}

	@Override
	public T getSelectedOption() {
		return (T) getSelectInput().getSelected();
	}

	@Override
	public void setSelectedOption(final T option) {
		getSelectInput().setSelected(option);
	}

	@Override
	public void clearSelectedOption() {
		getSelectInput().resetData();
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		getSelectInput().setReadOnly(readonly);
	}

	@Override
	public void setOptions(final List<T> options) {
		getSelectInput().reset();
		getSelectInput().setOptions(options);
	}

	@Override
	public List<T> getOptions() {
		return (List<T>) getSelectInput().getOptions();
	}

	@Override
	public void addOption(final T option) {
		List<T> options = getModifiableOptions();
		options.add(option);
		setOptions(options);
	}

	@Override
	public void removeOption(final T option) {
		List<T> options = getModifiableOptions();
		options.remove(option);
		setOptions(options);
	}

	@Override
	public void updateOption(final T item) {
		List<T> options = getModifiableOptions();
		options.remove(item);
		options.add(item);
		setOptions(options);
	}

	@Override
	public int getSize() {
		List<T> options = (List<T>) getSelectInput().getOptions();
		return options == null ? 0 : options.size();
	}

	@Override
	public void showView(final boolean show) {
		setContentVisible(show);
	}

	protected List<T> getModifiableOptions() {
		List<T> options = new ArrayList<>();
		List<T> current = (List<T>) getSelectInput().getOptions();
		if (current != null) {
			options.addAll(current);
		}
		return options;
	}

}
