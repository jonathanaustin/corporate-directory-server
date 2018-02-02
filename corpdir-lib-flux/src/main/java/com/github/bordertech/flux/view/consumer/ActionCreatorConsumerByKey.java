package com.github.bordertech.flux.view.consumer;

import com.github.bordertech.flux.ActionCreator;

/**
 * View is a ActionCreator Consumer.
 *
 * @param <S> the ActionCreator type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ActionCreatorConsumerByKey<S extends ActionCreator> extends StoreConsumer {

	void setActionCreatorKey(final String creatorKey);

	String getActionCreatorKey();

	S getActionCreatorByKey();
}
