package com.github.bordertech.wcomponents.lib.flux.wc;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.flux.Controller;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface WController extends Controller, WComponent {

	WMessages getViewMessages();

	void configViews();

	void configAjax(final WView view);

	void addView(final WView view);

	void removeView(final WView view);

	void resetViews();

}
