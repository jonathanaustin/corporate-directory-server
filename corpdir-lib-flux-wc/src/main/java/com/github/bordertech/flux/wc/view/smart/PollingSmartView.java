package com.github.bordertech.flux.wc.view.smart;

import com.github.bordertech.flux.wc.view.FluxSmartView;
import com.github.bordertech.flux.wc.view.dumb.PollingView;

/**
 * Smart view that is a Message Container.
 *
 * @author jonathan
 */
public interface PollingSmartView<T> extends FluxSmartView<T>, PollingView<T> {

	/**
	 *
	 * @return the polling view
	 */
	PollingView<T> getPollingView();
}
