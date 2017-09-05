package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.mode.FormMode;

/**
 * Form toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface FormToolbarView extends ToolbarView {

	FormMode getFormMode();

	void setFormMode(final FormMode mode);

	boolean isFormReady();

	void setFormReady(final boolean formReady);

}
