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
	 * Must be called after all the VIEWS have been added to the combo view
	 */
	void configViewDefaults();

}
