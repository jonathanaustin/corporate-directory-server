package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
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
	 * Check controller is configured correctly
	 */
	void checkConfig();

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
	 * @return the messages for this controller
	 */
	WMessages getViewMessages();

}
