package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.flux.impl.BasicView;

/**
 * Entity form view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityView<T> extends BasicView<T> {

	/**
	 * @return the entity action mode
	 */
	EntityMode getEntityMode();

	/**
	 * @param mode the entity action mode
	 */
	void setEntityMode(final EntityMode mode);

	/**
	 * Refresh the view state depending on the mode.
	 */
	void doRefreshViewState();

	boolean isFormReadOnly();
}
