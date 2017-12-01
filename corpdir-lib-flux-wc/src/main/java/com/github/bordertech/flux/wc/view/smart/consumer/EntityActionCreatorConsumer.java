package com.github.bordertech.flux.wc.view.smart.consumer;

import com.github.bordertech.flux.crud.actioncreator.EntityActionCreator;

/**
 * Entity Action Creator Consumer.
 *
 * @author jonathan
 */
public interface EntityActionCreatorConsumer<T> {

	void setEntityActionCreatorKey(final String creatorKey);

	String getEntityActionCreatorKey();

	EntityActionCreator<T> getEntityActionCreator();
}
