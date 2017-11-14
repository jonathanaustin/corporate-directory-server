package com.github.bordertech.wcomponents.lib.app.view.smart.polling;

import com.github.bordertech.flux.wc.view.SmartView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;

/**
 * Smart view that is a Message Container.
 *
 * @author jonathan
 */
public interface PollingSmartView<T> extends SmartView<T> {

	/**
	 *
	 * @return the polling view
	 */
	PollingView<T> getPollingView();
}
