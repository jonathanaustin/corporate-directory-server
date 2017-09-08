package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BaseView extends WComponent {

	/**
	 * The default qualifier separator character.
	 */
	String QUALIFIER_CONTEXT_SEPERATOR = "_";

	/**
	 * A qualifier must start with a letter and followed by letters, digits or dash.
	 */
	String QUALIFIER_VALIDATION_PATTERN = "[a-zA-Z][0-9a-zA-Z-]*";

	/**
	 *
	 * @return the dispatcher attached to this view.
	 */
	Dispatcher getDispatcher();

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
	 * Sometimes an event has to be given a more specific qualifier. The same event might happen more then once in a
	 * Combo View.
	 *
	 * @param qualifier the qualifier value
	 * @param types the event type to override the qualifier
	 */
	void addDispatcherOverride(final String qualifier, final EventType... types);

	/**
	 *
	 * @return the action model prefix
	 */
	String getPrefix();

}
