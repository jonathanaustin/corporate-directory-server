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
public interface RequiresServiceModel<S, T> extends Serializable {

	ServiceModel<S, T> getServiceModel();

	void setServiceModel(final ServiceModel<S, T> serviceModel);
}
