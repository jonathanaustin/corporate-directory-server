package com.github.bordertech.corpdir.web.ui.dumb;

import com.github.bordertech.corpdir.api.common.ApiObject;
import com.github.bordertech.flux.wc.view.dumb.InputOptionsView;
import com.github.bordertech.flux.wc.view.dumb.PollingView;
import com.github.bordertech.flux.wc.view.dumb.form.FormUpdateableView;
import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WField;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.lib.polling.PollingStatus;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.List;

/**
 * Basic API Form View.
 *
 * @param <T> the API object type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class BasicApiPanel<T extends ApiObject> extends FormUpdateableView<T> {

	private final WPanel formPanel = new WPanel();

	private final WFieldLayout formLayout = new WFieldLayout();

	private static final int LABEL_WIDTH = 30;

	/**
	 * Construct basic API panel.
	 *
	 * @param viewId the viewId
	 */
	public BasicApiPanel(final String viewId) {
		super(viewId);
		getContent().add(formPanel);
		formPanel.add(formLayout);
		formLayout.setLabelWidth(LABEL_WIDTH);

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

	/**
	 * Adds a simple text input field to the form.
	 *
	 * @param label The label that indicates to the user what information should be entered into this field.
	 * @param beanProperty The property this data will be stored in.
	 * @param mandatory true if this is a mandatory field (the user must enter a value for the form the be considered
	 * valid).
	 * @return the field which was added.
	 */
	protected final WField addTextField(final String label, final String beanProperty, final boolean mandatory) {
		return addInputField(new WTextField(), label, beanProperty, mandatory);
	}

	/**
	 * Adds a check box field to the form.
	 *
	 * @param label The label that indicates to the user what information this check box represents.
	 * @param beanProperty The property this data will be stored in.
	 * @param mandatory true if this is a mandatory field.
	 * @return the field which was added.
	 */
	protected final WField addCheckBox(final String label, final String beanProperty, final boolean mandatory) {
		return addInputField(new WCheckBox(), label, beanProperty, mandatory);
	}

	/**
	 * Adds an Input field to the form.
	 *
	 * @param input The input field to add to the form.
	 * @param label The label that indicates to the user what information this field represents.
	 * @param beanProperty The property this data will be stored in.
	 * @param mandatory true if this is a mandatory field for the purposes of validation.
	 * @return the field which was added.
	 */
	protected final WField addInputField(final Input input, final String label, final String beanProperty, final boolean mandatory) {
		input.setMandatory(mandatory);
		input.setBeanProperty(beanProperty);
		return getFormLayout().addField(label, input);
	}

	@Override
	public T getBeanValue() {
		return (T) super.getBeanValue();
	}

	@Override
	protected void validateComponent(final List<Diagnostic> diags) {
		super.validateComponent(diags);
		checkPollingInputs(diags, this);
	}

	protected void checkPollingInputs(final List<Diagnostic> diags, final WComponent component) {
		if (!component.isVisible()) {
			return;
		}
		// Check a polling options view has loaded correctly
		if (component instanceof PollingView && component instanceof InputOptionsView) {
			PollingView polling = (PollingView) component;
			if (polling.getPollingStatus() != PollingStatus.STOPPED) {
				Input input = ((InputOptionsView) component).getSelectInput();
				String lbl = input.getLabel() == null ? "" : input.getLabel().getText() + " ";
				diags.add(createErrorDiagnostic(input, "The " + lbl + "options have not been loaded."));
			}
			return;
		}
		if (component instanceof Container) {
			for (WComponent child : ((Container) component).getChildren()) {
				checkPollingInputs(diags, child);
			}
		}
	}

}
