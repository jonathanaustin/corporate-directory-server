package com.github.bordertech.wcomponents.lib.mvc;

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

}
