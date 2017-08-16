package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.flux.Controller;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicController extends Controller, WComponent {

	WMessages getViewMessages();

	void configViews();

	void configAjax(final BasicView view);

}
