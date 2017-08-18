package com.github.bordertech.wcomponents.lib.model;

import java.io.Serializable;

/**
 * Entity list view.
 *
 * @param <T> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface RequiresActionModel<T> extends Serializable {

	ActionModel<T> getActionModel();

	void setActionModel(final ActionModel<T> actionModel);
}
