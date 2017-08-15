package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 * @param <T> the View bean type
 */
public interface View<T> extends Serializable {

	/**
	 *
	 * @return the dispatcher attached to this view.
	 */
	Dispatcher getDispatcher();

	/**
	 * @return the view qualifier (if needed)
	 */
	String getQualifier();

	/**
	 *
	 * @return the view controller
	 */
	Controller getController();

	/**
	 * Helper method to dispatch an event for this event with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	void dispatchViewEvent(final EventType eventType);

	/**
	 * Helper method to dispatch an event for this event with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	void dispatchViewEvent(final EventType eventType, final Object data);

	/**
	 * A helper method to register a listener with an Event Type and the View qualifier will be automatically added.
	 *
	 * @param listener
	 * @param eventType
	 * @return the listener register id
	 */
	String registerViewListener(final Listener listener, final EventType eventType);

	/**
	 * @return the view bean
	 */
	T getViewBean();

	/**
	 * @param viewBean the view bean
	 */
	void setViewBean(final T viewBean);

}
