package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.model.Model;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Controller extends WComponent {

	/**
	 *
	 * @return the dispatcher attached to this controller.
	 */
	Dispatcher getDispatcher();

	/**
	 * @return the qualifier to be used on listeners or events (if needed)
	 */
	String getQualifier();

	/**
	 *
	 * @return the views added to this controller
	 */
	List<View> getViews();

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
	 * @param view the view to add to this controller
	 */
	void addView(final View view);

	/**
	 *
	 * @param view the view to remove from this controller
	 */
	void removeView(final View view);

	/**
	 * Reset the views.
	 */
	void resetViews();

	/**
	 *
	 * @return the messages for this controller
	 */
	WMessages getViewMessages();

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