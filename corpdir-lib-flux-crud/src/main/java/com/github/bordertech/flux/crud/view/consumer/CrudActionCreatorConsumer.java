package com.github.bordertech.flux.crud.view.consumer;

import com.github.bordertech.flux.crud.actioncreator.CrudActionCreator;
import com.github.bordertech.flux.view.consumer.ActionCreatorConsumer;

/**
 * View is a CRUD Entity Action Creator Consumer.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudActionCreatorConsumer<T> extends ActionCreatorConsumer {

	void setEntityActionCreatorKey(final String creatorKey);

	String getEntityActionCreatorKey();

	CrudActionCreator<T> getEntityActionCreator();
}
