package com.github.bordertech.wcomponents.lib.app.view.input.impl;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.event.SelectEventType;
import com.github.bordertech.wcomponents.lib.app.view.input.SingleSelectOptionsView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @param <T> the options type
 * @author jonathan
 */
public abstract class AbstractSingleSelectOptionsView<T> extends DefaultView<T> implements SingleSelectOptionsView<T> {

	public AbstractSingleSelectOptionsView() {
		setBeanProperty(".");
		setSearchAncestors(true);
	}

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractSingleSelectOptionsView.class);

	protected void setupInputAjax() {
		// Add AJAX COntrol for Select Input
		if (getSelectInput() instanceof AjaxTrigger) {
			AppAjaxControl ajax = new AppAjaxControl((AjaxTrigger) getSelectInput());
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
		if (isIncludeNullOption() && !options.contains(null)) {
			List<T> withNull = new ArrayList<>(options);
			withNull.add(0, null);
			getSelectInput().setOptions(withNull);
		} else {
			getSelectInput().setOptions(options);
		}
	}

	@Override
	public List<T> getOptions() {
		return (List<T>) getSelectInput().getOptions();
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

	@Override
	public boolean isIncludeNullOption() {
		return getComponentModel().includeNullOption;
	}

	@Override
	public void setIncludeNullOption(final boolean includeNullOption) {
		getOrCreateComponentModel().includeNullOption = includeNullOption;
	}

	@Override
	public void setCodeProperty(final String codeProperty) {
		getOrCreateComponentModel().codeProperty = codeProperty;
	}

	@Override
	public String getCodeProperty() {
		return getComponentModel().codeProperty;
	}

	protected boolean isBoundByCode() {
		return getCodeProperty() != null;
	}

	@Override
	public Object getBeanValue() {
		Object beanValue = super.getBeanValue();
		if (isBoundByCode()) {
			return getOptionForCode(beanValue);
		}
		return beanValue;
	}

	@Override
	public void updateBeanValue() {
		T selected = getSelectedOption();
		if (isBoundByCode()) {
			String beanProperty = getCodeProperty();
			Object code = getCodeForOption(selected, beanProperty);
			doUpdateBeanValue(code);
		} else {
			doUpdateBeanValue(selected);
		}
	}

	protected T getOptionForCode(final Object code) {
		List<T> options = getOptions();
		if (options == null) {
			return null;
		}
		String beanProperty = getCodeProperty();
		for (T option : options) {
			Object optCode = getCodeForOption(option, beanProperty);
			if (Objects.equals(code, optCode)) {
				return option;
			}
		}
		return null;
	}

	protected Object getCodeForOption(final T option, final String beanProperty) {
		if (option == null) {
			return null;
		}
		Object code = null;
		if (beanProperty == null || ".".equals(beanProperty)) {
			code = option;
		} else {
			try {
				code = PropertyUtils.getProperty(option, beanProperty);
			} catch (Exception e) {
				LOG.error("Failed to read bean property " + beanProperty + " from " + option, e);
			}
		}
		return code;
	}

	@Override
	protected SingleSelectModel newComponentModel() {
		return new SingleSelectModel();
	}

	@Override
	protected SingleSelectModel getComponentModel() {
		return (SingleSelectModel) super.getComponentModel();
	}

	@Override
	protected SingleSelectModel getOrCreateComponentModel() {
		return (SingleSelectModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class SingleSelectModel extends ViewModel {

		private String codeProperty;

		private boolean includeNullOption;
	}

}
