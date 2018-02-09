package com.github.bordertech.flux.wc.view.dumb;

import com.github.bordertech.flux.wc.view.FluxDumbView;
import com.github.bordertech.wcomponents.addons.polling.Pollable;

/**
 * Polling View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface PollingView<T> extends FluxDumbView<T>, Pollable {

	void setContineStart(final boolean start);

	boolean isContinueStart();

}
