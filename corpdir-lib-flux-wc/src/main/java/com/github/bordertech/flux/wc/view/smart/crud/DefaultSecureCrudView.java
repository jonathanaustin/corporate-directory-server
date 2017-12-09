package com.github.bordertech.flux.wc.view.smart.crud;

import com.github.bordertech.flux.wc.view.smart.secure.SecureCardView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.security.AppPath;

/**
 * Default secure CRUD view.
 *
 * @author jonathan
 */
public class DefaultSecureCrudView<S, T> extends DefaultCrudSmartView<S, T> implements SecureCardView<T> {

	private final AppPath path;

	public DefaultSecureCrudView(final AppPath path, final String viewId, final String title, final WComponent panel) {
		super(viewId, title, panel);
		this.path = path;
	}

	@Override
	public AppPath getAppPath() {
		return path;
	}

	@Override
	public void handleCardRequest(final Request request) {
	}

}
