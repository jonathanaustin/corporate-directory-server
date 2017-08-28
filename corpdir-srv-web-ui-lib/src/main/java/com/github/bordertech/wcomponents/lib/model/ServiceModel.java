package com.github.bordertech.wcomponents.lib.model;

/**
 *
 * @author jonathan
 */
public interface ServiceModel<S, T> extends Model {

	T service(final S criteria);

}
