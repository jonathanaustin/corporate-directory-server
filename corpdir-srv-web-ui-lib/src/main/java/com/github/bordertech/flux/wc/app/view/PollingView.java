package com.github.bordertech.flux.wc.app.view;

import com.github.bordertech.wcomponents.lib.polling.Pollable;
import com.github.bordertech.flux.wc.view.FluxDumbView;

/**
 * Polling View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingView<T> extends FluxDumbView<T>, Pollable {

}
