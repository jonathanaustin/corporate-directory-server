package com.github.bordertech.flux.wc.view.smart.secure;

import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.lib.security.AppPath;

/**
 * Smart View that is a secure card.
 *
 * @param <T> the view bean type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultSecureCardView<T> extends DefaultSmartView<T> implements SecureCardView<T> {

	private final AppPath appPath;

	public DefaultSecureCardView(final String viewId, final AppPath appPath) {
		super(viewId);
		this.appPath = appPath;
	}

	public DefaultSecureCardView(final String viewId, final String templateName, final AppPath appPath) {
		super(viewId, templateName);
		this.appPath = appPath;
	}

	@Override
	public AppPath getAppPath() {
		return appPath;
	}

	@Override
	public void handleShowCardRequest(final Request request) {
		// Do nothing
	}

	@Override
	public void handleCheckCardRequest(final Request request) {
		// Do nothing
	}

}
