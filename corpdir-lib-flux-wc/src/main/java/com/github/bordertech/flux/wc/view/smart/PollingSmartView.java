package com.github.bordertech.flux.wc.view.smart;

import com.github.bordertech.flux.wc.view.FluxSmartView;
import com.github.bordertech.flux.wc.view.dumb.PollingView;

/**
 * Smart view that is a POlling View.
 *
 * @param <T> the view bean type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PollingSmartView<T> extends FluxSmartView<T>, PollingView<T> {

	/**
	 *
	 * @return the polling view
	 */
	PollingView<T> getPollingView();
}
