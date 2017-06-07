package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.WComponent;

/**
 * Entity form view.
 *
 * @param <T> the entity type
 * @param <R> the entity id
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface EntityView<T, R> extends WComponent, BeanBound {

	/**
	 * @return the bean being displayed.
	 */
	T getEntity();

	/**
	 * @return the entity id
	 */
	R getEntityId();

	/**
	 * @return true if form read only
	 */
	boolean isFormReadOnly();

	/**
	 *
	 * @param formReadOnly true if form read only
	 */
	void setFormReadOnly(final boolean formReadOnly);

	/**
	 *
	 * @return true if an existing entity
	 */
	boolean isExistingEntity();

	/**
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(boolean searchAncestors);

}
