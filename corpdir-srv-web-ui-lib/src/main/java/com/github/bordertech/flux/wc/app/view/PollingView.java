package com.github.bordertech.flux.wc.app.view;

import com.github.bordertech.flux.view.DumbView;
import com.github.bordertech.wcomponents.lib.polling.Pollable;

/**
 * Polling View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingView<T> extends DumbView<T>, Pollable {

}
