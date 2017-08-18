package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.mode.EntityMode;
import com.github.bordertech.wcomponents.lib.flux.impl.WView;

/**
 * Entity form view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityView<T> extends WView<T> {

	/**
	 * @return the entity action mode
	 */
	EntityMode getEntityMode();

	/**
	 * @param mode the entity action mode
	 */
	void setEntityMode(final EntityMode mode);

	/**
	 * @return true if form is in read only
	 */
	boolean isFormReadOnly();

	/**
	 *
	 * @return true if entity has been loaded
	 */
	boolean isLoaded();

	/**
	 *
	 * @param entity the entity to load into this view
	 */
	void loadEntity(final T entity);

}
