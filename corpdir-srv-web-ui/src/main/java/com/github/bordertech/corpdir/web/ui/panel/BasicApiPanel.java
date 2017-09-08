package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.common.ApiObject;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WField;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;

/**
 * Basic API Form View.
 *
 * @param <T> the API object type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class BasicApiPanel<T extends ApiObject> extends DefaultView<T> {

	private final WPanel formPanel = new WPanel();

	private final WFieldLayout formLayout = new WFieldLayout();

	/**
	 * Construct basic detail panel. \
	 */
	public BasicApiPanel(final String qualifier) {
		super(qualifier);
		getContent().add(formPanel);
		formPanel.add(formLayout);
		formLayout.setLabelWidth(30);

		setBeanProperty(".");
		setSearchAncestors(true);
	}

	/**
	 * @return the panel that hold the form details
	 */
	protected final WPanel getFormPanel() {
		return formPanel;
	}

	/**
	 * @return the field layout holding the basic input fields
	 */
	protected final WFieldLayout getFormLayout() {
		return formLayout;
	}

	protected final WField addTextField(final String label, final String beanProperty, final boolean mandatory) {
		return addInputField(new WTextField(), label, beanProperty, mandatory);
	}

	protected final WField addCheckBox(final String label, final String beanProperty, final boolean mandatory) {
		return addInputField(new WCheckBox(), label, beanProperty, mandatory);
	}

	protected final WField addInputField(final Input input, final String label, final String beanProperty, final boolean mandatory) {
		input.setMandatory(mandatory);
		input.setBeanProperty(beanProperty);
		return getFormLayout().addField(label, input);
	}

	@Override
	public T getBeanValue() {
		return (T) super.getBeanValue();
	}

}
