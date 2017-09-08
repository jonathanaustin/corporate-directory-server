package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.model.Model;

/**
 * Is a combination of views (ie composite).
 *
 * @author Jonathan Austin
 * @param <T> the view bean data type
 * @since 1.0.0
 *
 */
public interface ComboView<T> extends View<T> {

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
	 * @param key the key for the model
	 * @param model the model for this controller
	 */
	void addModel(final String key, final Model model);

	/**
	 *
	 * @param key the key for the model
	 * @return the model instance
	 */
	Model getModel(final String key);

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

	/**
	 * @param id the listener id to register
	 */
	void registerListenerId(final String id);

	/**
	 * Sometimes an event has to be given a more specific qualifier. The same event might happen more then once in a
	 * Combo View.
	 *
	 * @param qualifier the qualifier value
	 * @param types the event type to override the qualifier
	 */
	void addListenerOverride(final String qualifier, final EventType... types);

}
