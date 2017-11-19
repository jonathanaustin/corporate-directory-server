package com.github.bordertech.flux.wc.app.view.smart.consumer;

import com.github.bordertech.flux.app.actioncreator.EntityActionCreator;

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
