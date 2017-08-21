package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.flux.wc.WViewBound;
import com.github.bordertech.wcomponents.lib.polling.PollingService;

/**
 * Polling View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingView<S, T> extends WViewBound<T>, PollingService<S, T> {
}
