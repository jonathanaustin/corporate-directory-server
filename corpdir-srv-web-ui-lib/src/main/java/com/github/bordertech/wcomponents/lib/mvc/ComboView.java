package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.lib.flux.EventType;

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
	 *
	 * @return true if block searching upwards for AJAX.
	 */
	boolean isBlocking();

	/**
	 *
	 * @param blocking true if block searching upwards for AJAX or Models
	 */
	void setBlocking(final boolean blocking);

	/**
	 * Sometimes an event has to be given a more specific qualifier. The same event might happen more then once in a
	 * Combo View. Add it too all the combo view controllers.
	 *
	 * @param qualifier the qualifier value
	 * @param types the event type to override the qualifier
	 */
	void addListenerOverride(final String qualifier, final EventType... types);

}
