package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.lib.flux.EventType;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Controller extends BaseMvc {

	/**
	 * Check controller is configured correctly
	 */
	void checkConfig();

	/**
	 * Setup the controller listeners.
	 */
	void setupController();

	/**
	 *
	 * @return the views added to this controller
	 */
	List<View> getViews();

	/**
	 * Reset the views.
	 */
	void resetViews();

	/**
	 *
	 * @param view the view to control
	 */
	void addView(final View view);

	/**
	 * Sometimes an event has to be given a more specific qualifier. The same event might happen more then once in a
	 * Combo View.
	 *
	 * @param qualifier the qualifier value
	 * @param types the event type to override the qualifier
	 */
	void addListenerOverride(final String qualifier, final EventType... types);

}
