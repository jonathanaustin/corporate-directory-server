package com.github.bordertech.flux.view;

import java.util.List;
import java.util.Set;

/**
 *
 * Smart views are the controllers of Dumb Views.
 * <p>
 * Smart Views interact with ActionCreators and Stores. They control and pass data to their child dumb views.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface SmartView<T> extends DumbView<T> {

	/**
	 * The default qualifier separator character.
	 */
	String QUALIFIER_CONTEXT_SEPERATOR = "_";

	/**
	 * @return true if this view is a qualifier context
	 */
	boolean isQualifierContext();

	/**
	 *
	 * @param qualifierContext true if this view is a qualifier context
	 */
	void setQualifierContext(final boolean qualifierContext);

	/**
	 * Called by child views.
	 *
	 * @param viewId the view id the event originated from
	 * @param eventType the child view event to handle
	 * @param data the associated data
	 */
	void serviceViewEvent(final String viewId, final ViewEventType eventType, final Object data);

	/**
	 * @return the qualifier to be used on listeners or events (if needed)
	 */
	String getQualifier();

	/**
	 *
	 * @param qualifier the qualifier to be used on events
	 */
	void setQualifier(final String qualifier);

	/**
	 * @return the context qualifier
	 */
	String getFullQualifier();

	/**
	 * Child views.
	 *
	 * @return the list of child views
	 */
	List<? extends DumbView> getViews();

	/**
	 *
	 * @param viewId the view id to return
	 * @return the view for the view id
	 */
	DumbView getView(final String viewId);

	/**
	 * This is used when a smart view is being used as a dumb view.
	 *
	 * @param dumbMode true if need to pass events for this view to its parent.
	 */
	void setDumbMode(final boolean dumbMode);

	/**
	 *
	 * @return true if pass events to its parent smart view
	 */
	boolean isDumbMode();

	/**
	 * Pass an event to the parent smart view to process.
	 *
	 * @param type the event type to pass through
	 */
	void addPassThrough(final ViewEventType type);

	/**
	 * Remove an event type pass through.
	 *
	 * @param type the event type
	 */
	void removePassThrough(final ViewEventType type);

	/**
	 *
	 * @return the events this smart view should pass to its parent smart view
	 */
	Set<ViewEventType> getPassThroughs();

	/**
	 * Helper method to set if a smart view should send all its events to its parent smart view.
	 *
	 * @param passAllEvents true if pass all events to the parent smart view
	 */
	void setPassAllEvents(final boolean passAllEvents);

	/**
	 *
	 * @return true if pass all events to the parent smart view
	 */
	boolean isPassAllEvents();
}
