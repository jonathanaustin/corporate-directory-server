package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.lib.model.Model;

/**
 * Is a combination of views (ie composite).
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ComboView extends View {

	/**
	 * Configure the views.
	 */
	void configViews();

	/**
	 *
	 * @param view the view to add any AJAX details to
	 */
	void configAjax(final View view);

	/**
	 *
	 * @param model the model for this controller
	 */
	void addModel(final Model model);

	/**
	 *
	 * @param clazz the model class to return
	 * @return the model instance
	 */
	Model getModel(final Class clazz);

	/**
	 *
	 * @return true if block searching upwards for AJAX and Models
	 */
	boolean isBlocking();

	/**
	 *
	 * @param blocking true if block searching upwards for AJAX or Models
	 */
	void setBlocking(final boolean blocking);
}
