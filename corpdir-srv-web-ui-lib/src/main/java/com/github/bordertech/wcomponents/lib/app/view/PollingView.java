package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.polling.PollableService;
import com.github.bordertech.wcomponents.polling.ServiceAction;
import com.github.bordertech.flux.wc.view.View;

/**
 * Polling View.
 *
 * @author Jonathan Austin
 * @param <S> the criteria type
 * @param <T> the response type
 * @since 1.0.0
 *
 */
public interface PollingView<S, T> extends View<T>, PollableService<S, T> {

	void doSetupAndStartPolling(final S criteria, final ServiceAction<S, T> serviceModel);
}
