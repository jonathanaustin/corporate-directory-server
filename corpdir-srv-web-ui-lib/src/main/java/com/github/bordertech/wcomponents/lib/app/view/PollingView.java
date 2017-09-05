package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.mvc.ViewBound;
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
public interface PollingView<S, T> extends ViewBound<T>, PollingService<S, T> {
}
