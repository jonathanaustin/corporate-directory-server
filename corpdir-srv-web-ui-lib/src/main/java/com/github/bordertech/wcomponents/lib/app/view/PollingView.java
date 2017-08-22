package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.polling.PollingService;
import com.github.bordertech.wcomponents.lib.mvc.ViewBound;

/**
 * Polling View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingView<S, T> extends ViewBound<T>, PollingService<S, T> {
}
