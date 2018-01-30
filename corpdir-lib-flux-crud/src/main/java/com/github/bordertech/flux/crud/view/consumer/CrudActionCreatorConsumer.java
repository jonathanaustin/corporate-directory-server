package com.github.bordertech.flux.crud.view.consumer;

import com.github.bordertech.flux.crud.actioncreator.CrudActionCreator;
import com.github.bordertech.flux.view.consumer.ActionCreatorConsumerByKey;

/**
 * View is a CRUD Entity Action Creator Consumer.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudActionCreatorConsumer<T> extends ActionCreatorConsumerByKey<CrudActionCreator<T>> {
}
