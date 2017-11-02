package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.lib.app.mode.FormMode;
import com.github.bordertech.flux.wc.view.DumbView;

/**
 * Form view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface FormView<T> extends DumbView<T> {

	/**
	 * @return the form mode
	 */
	FormMode getFormMode();

	/**
	 * @param mode the form mode
	 */
	void setFormMode(final FormMode mode);

	/**
	 *
	 * @return true if entity has been loaded
	 */
	boolean isLoaded();

	/**
	 *
	 * @return true if form is readonly
	 */
	boolean isFormReadonly();

	/**
	 *
	 * @param entity the entity to load into this view
	 * @param mode the entity mode
	 */
	void loadEntity(final T entity, final FormMode mode);

	/**
	 * @return the entity details holder
	 */
	WContainer getFormHolder();

}
