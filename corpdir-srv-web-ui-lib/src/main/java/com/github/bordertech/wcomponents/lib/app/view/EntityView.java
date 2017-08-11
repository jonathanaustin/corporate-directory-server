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
public interface EntityView<T> extends BasicView {


	/**
	 *
	 * @param entity the entity to view
	 */
	void setEntity(final T entity);

	/**
	 * @return the entity being displayed.
	 */
	T getEntity();

	/**
	 * @param mode the entity action mode
	 */
	void setEntityMode(final EntityMode mode);

	/**
	 * @return the entity action mode
	 */
	EntityMode getEntityMode();

	/**
	 * Refresh the view state depending on the mode.
	 */
	void doRefreshViewState();

}
