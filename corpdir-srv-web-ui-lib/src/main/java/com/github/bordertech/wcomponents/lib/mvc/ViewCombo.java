package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.lib.model.Model;

/**
 * Is a combination of views (ie composite).
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ViewCombo extends View {

	/**
	 * Called by the controller for this combo view.
	 */
	void configComboViews();

	/**
	 *
	 * @param model the model required by this view
	 */
	void addModel(final Model model);

	/**
	 * Make view a blocking view. Dont search upwards for AJAX and Models.
	 */
	void makeBlocking();
}
