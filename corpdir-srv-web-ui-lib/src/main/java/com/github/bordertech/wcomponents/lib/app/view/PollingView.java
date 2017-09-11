package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.polling.PollableModel;
import com.github.bordertech.wcomponents.lib.polling.PollableService;

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

	void doSetupAndStartPolling(final S criteria, final PollableModel<S, T> serviceModel);
}
