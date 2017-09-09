package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.app.model.ServiceModel;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.polling.PollingService;

/**
 * Polling View.
 *
 * @author Jonathan Austin
 * @param <S> the criteria type
 * @param <T> the response type
 * @since 1.0.0
 *
 */
public interface PollingView<S, T> extends View<T>, PollingService<S, T> {

	void doSetupAndStartPolling(final S criteria, final ServiceModel<S, T> serviceModel);
}
