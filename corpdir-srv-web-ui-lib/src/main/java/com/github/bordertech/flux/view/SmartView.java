package com.github.bordertech.flux.view;

import com.github.bordertech.flux.event.ViewEventType;
import java.util.List;

/**
 * Smart View.
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
	void handleViewEvent(final String viewId, final ViewEventType eventType, final Object data);

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
	List<DumbView> getViews();

	/**
	 * A child View can ask the container to configure its AJAX requirements.
	 *
	 * @param view the view to add any AJAX details to
	 */
	void configAjax(final DumbView view);

	/**
	 * @return true if this view is an AJAX context
	 */
	boolean isAjaxContext();

	/**
	 *
	 * @param ajaxContext true if this view is an AJAX context
	 */
	void setAjaxContext(final boolean ajaxContext);

}
