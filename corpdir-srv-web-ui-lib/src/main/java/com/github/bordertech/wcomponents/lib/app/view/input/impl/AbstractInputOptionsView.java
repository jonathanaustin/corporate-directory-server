package com.github.bordertech.wcomponents.lib.app.view.input.impl;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.app.event.SelectEventType;
import com.github.bordertech.wcomponents.lib.app.view.input.InputOptionsView;
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
public abstract class AbstractInputOptionsView<T> extends DefaultView<T> implements InputOptionsView<T> {

	public AbstractInputOptionsView() {
		setBeanProperty(".");
		setSearchAncestors(true);
	}

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractInputOptionsView.class);

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

	protected T getOptionForCode(final Object code) {
		return getOptionForCode(code, getOptions(), getCodeProperty());
	}

	protected T getOptionForCode(final Object code, final List<T> options, String beanProperty) {
		if (options == null) {
			return null;
		}
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

	abstract protected void doDispatchSelectEvent();

	protected void registerSelectUnselectAjaxControl(final AppAjaxControl ctrl) {
		registerEventAjaxControl(SelectEventType.UNSELECT, ctrl);
		registerEventAjaxControl(SelectEventType.SELECT, ctrl);
	}

	@Override
	protected InputOptionsModel newComponentModel() {
		return new InputOptionsModel();
	}

	@Override
	protected InputOptionsModel getComponentModel() {
		return (InputOptionsModel) super.getComponentModel();
	}

	@Override
	protected InputOptionsModel getOrCreateComponentModel() {
		return (InputOptionsModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class InputOptionsModel extends ViewModel {

		private String codeProperty;

		private boolean includeNullOption;
	}

}
