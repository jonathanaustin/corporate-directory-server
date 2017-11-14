package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.flux.wc.view.DumbView;
import com.github.bordertech.wcomponents.polling.Pollable;

/**
 * Polling View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingView<T> extends DumbView<T>, Pollable {

}
